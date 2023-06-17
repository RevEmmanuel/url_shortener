package org.example.data.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateLinkRequest {

    @NotNull(message = "id cannot be blank")
    private Long id;

    @NotBlank(message = "title cannot be blank")
    private String title;

    @NotBlank(message = "url cannot be blank")
    private String url;

}
