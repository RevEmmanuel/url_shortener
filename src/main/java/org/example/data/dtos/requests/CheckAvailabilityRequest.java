package org.example.data.dtos.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CheckAvailabilityRequest {

    private Long id;
    private String linkName;

}
