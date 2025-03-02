package org.example.plain.domain.homework.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class WorkMemberId implements Serializable {
    @Column(name = "h_id")
    private String work;

    @Column(name = "user_id")
    private String memberUser;
}
