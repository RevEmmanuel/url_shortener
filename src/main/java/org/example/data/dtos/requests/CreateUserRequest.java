package org.example.data.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserRequest {

    @NotBlank(message = "Username is required")
    private String username;

    @Email(message = "Invalid email address")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    private String lastName;

    @Pattern(regexp = "^((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\\W).{8,64})$",
            message = "Password must be between 8 - 64 characters and must contain at least 1 uppercase, 1 lowercase," +
                    " 1 number and 1 special " +
                    "character")
    @NotBlank(message = "Password is required")
    private String password;
}
