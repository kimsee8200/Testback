package org.example.plain.domain.groupmember.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class GroupMemberId implements Serializable {

    @Column(name = "g_id")
    private String groupId;

    @Column(name = "user_id")
    private String userId;
}
