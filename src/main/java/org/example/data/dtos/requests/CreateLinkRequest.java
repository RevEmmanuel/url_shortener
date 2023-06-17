package org.example.data.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateLinkRequest {

    @NotBlank(message = "url cannot be blank")
    private String url;

    @NotBlank(message = "title cannot be blank")
    private String title;

}
