package org.example.data.dtos.requests;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MailInfo {
    private String name;
    private String email;
}

