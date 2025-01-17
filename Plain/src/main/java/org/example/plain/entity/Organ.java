package org.example.plain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.plain.dto.OrganDTO;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Organ {
    @Id
    @Column(length = 100, nullable = false)
    private String organId;

    @Column(columnDefinition = "tinytext", nullable = false)
    private String organName;

    public OrganDTO toDTO() {
        return OrganDTO.builder()
                .organId(organId)
                .organName(organName)
                .build();
    }
}
