package org.example.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.data.dtos.requests.DeleteUserRequest;
import org.example.data.models.Roles;
import org.example.exceptions.UserNotAuthorizedException;
import org.example.exceptions.UserNotFoundException;
import org.example.data.dtos.requests.CreateUserRequest;
import org.example.data.dtos.requests.LoginRequest;
import org.example.data.dtos.responses.CreateUserResponse;
import org.example.data.dtos.responses.FindUserResponse;
import org.example.data.dtos.responses.LoginResponse;
import org.example.data.models.UserEntity;
import org.example.data.repositories.UserRepository;
import org.example.exceptions.InvalidLoginDetailsException;
import org.example.exceptions.UsernameAlreadyExistsException;
import org.example.security.AuthenticatedUser;
import org.example.security.JwtUtils;
import org.example.services.cloud.CloudService;
import org.example.services.email.EmailService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final EmailService emailService;
    private final CloudService cloudService;

    @Override
    public CreateUserResponse createUser(CreateUserRequest userRequest){
        if (userRepository.existsByUsername(userRequest.getUsername())) throw new UsernameAlreadyExistsException();
        if (userRepository.existsByEmail(userRequest.getEmail())) throw new UsernameAlreadyExistsException("Email is taken");
        UserEntity newUser = UserEntity.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .username(userRequest.getUsername())
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .isEnabled(true)
                .roles(Set.of(Roles.USER))
                .build();
        String htmlContent = "<html>" +
                "<head></head>" +
                "<body>" +
                "<p>Hi there, " + newUser.getFirstName() + "!</p>" +
                "<p>Welcome to You-RL shortener. We're glad to have you!</p>" +
                "</body></html>";
        emailService.sendEmail(newUser.getEmail(), "Welcome to You-RL", htmlContent);
        UserEntity savedUser = userRepository.save(newUser);
        LoginResponse response = this.generateTokens(new HashMap<>(), savedUser.getEmail());
        return CreateUserResponse.builder()
                .id(savedUser.getId())
                .lastName(savedUser.getLastName())
                .firstName(savedUser.getFirstName())
                .email(savedUser.getEmail())
                .username(savedUser.getUsername())
                .loginResponse(response)
                .build();
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                            loginRequest.getPassword())
            );

            Map<String, Object> claims = authentication.getAuthorities().stream()
                    .collect(Collectors.toMap(authority -> "claim", Function.identity()));
            AuthenticatedUser user = (AuthenticatedUser) authentication.getPrincipal();
            String email = user.getUser().getEmail();
            return this.generateTokens(claims, email);

        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidLoginDetailsException("Invalid login details");
        }
    }

    @Override
    public UserEntity getCurrentUser() {
        try {
            AuthenticatedUser authenticatedUser
                    = (AuthenticatedUser) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();
            return authenticatedUser.getUser();
        } catch (Exception e) {
            throw new UserNotAuthorizedException();
        }
    }

    @Override
    public FindUserResponse findUserByUserName(String userName) {
        UserEntity foundUser = userRepository.findByUsername(userName).orElseThrow(UserNotFoundException::new);
        return FindUserResponse.builder()
                .id(foundUser.getId())
                .lastName(foundUser.getLastName())
                .firstName(foundUser.getFirstName())
                .email(foundUser.getEmail())
                .username(foundUser.getUsername())
                .password(foundUser.getPassword())
                .build();
    }

    @Override
    public String deleteUser(DeleteUserRequest deleteUserRequest) {
        Long userId = deleteUserRequest.getUserId();
        if (!Objects.equals(userId, this.getCurrentUser().getId())) throw new UserNotAuthorizedException();
        UserEntity foundUser = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        if (passwordEncoder.matches(deleteUserRequest.getPassword(), foundUser.getPassword())) userRepository.delete(foundUser);
        return "SUCCESSFUL";
    }

    @Override
    public String uploadProfileImage(MultipartFile profileImage) {
        UserEntity foundUser = this.getCurrentUser();
        String imageUrl = cloudService.upload(profileImage);
        foundUser.setProfileImage(imageUrl);
        userRepository.save(foundUser);
        return imageUrl;
    }

    @Override
    public String deleteProfileImage() {
        UserEntity foundUser = this.getCurrentUser();
        foundUser.setProfileImage("");
        userRepository.save(foundUser);
        return "SUCCESSFUL";
    }

    @Override
    public List<FindUserResponse> findAll() {
        return userRepository.findAll().stream().map((foundUser) -> FindUserResponse.builder()
                .id(foundUser.getId())
                .lastName(foundUser.getLastName())
                .firstName(foundUser.getFirstName())
                .email(foundUser.getEmail())
                .username(foundUser.getUsername())
                .password(foundUser.getPassword())
                .build()).collect(Collectors.toList());
    }

    private LoginResponse generateTokens(Map<String, Object> claims, String email) {
        String accessToken = jwtUtils.generateAccessToken(claims, email);
        String refreshToken = jwtUtils.generateRefreshToken(email);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}

