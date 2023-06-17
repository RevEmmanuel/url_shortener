package org.example.data.dtos.responses;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class LinkDTO {

    private Long id;

    private String url;

    private String title;

    private String linkName;

    private final LocalDateTime dateCreated;

    private LocalDateTime dateLastModified;

}
