package org.example.data.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest {

    @NotBlank(message = "Username cannot be blank")
    private String email;
    @NotBlank(message = "Password cannot be blank")
    private String password;

}
