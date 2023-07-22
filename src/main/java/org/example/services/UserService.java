package org.example.services;

import org.example.data.dtos.requests.CreateUserRequest;
import org.example.data.dtos.requests.DeleteUserRequest;
import org.example.data.dtos.requests.LoginRequest;
import org.example.data.dtos.requests.UpdateUserDetailsRequest;
import org.example.data.dtos.responses.CreateUserResponse;
import org.example.data.dtos.responses.FindUserResponse;
import org.example.data.dtos.responses.LoginResponse;
import org.example.data.models.UserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    CreateUserResponse createUser(CreateUserRequest userRequest);

    LoginResponse login(LoginRequest loginRequest);

    UserEntity getCurrentUser();

    FindUserResponse findUserByUserName(String userName);

    String deleteUser(DeleteUserRequest deleteRequest);

    String uploadProfileImage(MultipartFile profileImage);

    String deleteProfileImage();

    List<FindUserResponse> findAll();

    FindUserResponse updateUserDetails(UpdateUserDetailsRequest updateUserDetailsRequest);
}

