package org.example.plain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Organ {
    @Id
    @Column(length=100, nullable = false)
    private String o_id;

    @Column(columnDefinition = "tinytext", nullable = false)
    private String o_name;
}
