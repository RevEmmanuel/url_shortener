package org.example.data.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KhalidVideo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String videoUrl;

    @Column(unique = true)
    private String name;

}
