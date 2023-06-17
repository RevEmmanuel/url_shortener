package org.example.data.dtos.responses;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CreateUserResponse {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private LoginResponse loginResponse;

}
