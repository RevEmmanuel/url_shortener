package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.exceptions.UserNotAuthorizedException;
import org.example.security.AppUtils;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;

    @Override
    public CreateUserResponse createUser(CreateUserRequest userRequest){
        if (userRepository.existsByUsername(userRequest.getUsername())) throw new UsernameAlreadyExistsException();
        UserEntity newUser = UserEntity.builder().build();
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
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                            loginRequest.getPassword())
            );

            Map<String, Object> claims = authentication.getAuthorities().stream()
                    .collect(Collectors.toMap(authority -> "claim", Function.identity()));
            AuthenticatedUser user = (AuthenticatedUser) authentication.getPrincipal();
            String email = user.getUser().getEmail();

            return this.generateTokens(claims, email);

        } catch (Exception e) {
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
        return null;
    }

    @Override
    public String deleteUserById(Long userId) {
        return null;
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

