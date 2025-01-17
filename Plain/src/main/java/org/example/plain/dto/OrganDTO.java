package org.example.plain.dto;

import lombok.*;
import org.example.plain.entity.Organ;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrganDTO {
    private String organId;
    private String organName;

    public Organ toEntity() {
        return Organ.builder()
                .organId(organId)
                .organName(organName)
                .build();
    }
}
