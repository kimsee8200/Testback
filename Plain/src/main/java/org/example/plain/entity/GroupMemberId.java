package org.example.plain.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class GroupMemberId implements Serializable {
    private String groupId;
    private String userId;
}
