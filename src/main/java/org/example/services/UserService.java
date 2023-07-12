package org.example.services;

import org.example.data.dtos.requests.CreateUserRequest;
import org.example.data.dtos.requests.LoginRequest;
import org.example.data.dtos.responses.CreateUserResponse;
import org.example.data.dtos.responses.FindUserResponse;
import org.example.data.dtos.responses.LoginResponse;
import org.example.data.models.UserEntity;

public interface UserService {
    CreateUserResponse createUser(CreateUserRequest userRequest);

    LoginResponse login(LoginRequest loginRequest);

    UserEntity getCurrentUser();

    FindUserResponse findUserByUserName(String userName);

    String deleteUserById(Long userId);
}

