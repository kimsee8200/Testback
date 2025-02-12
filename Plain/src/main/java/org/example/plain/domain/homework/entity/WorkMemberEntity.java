package org.example.plain.domain.homework.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.example.plain.domain.group.entity.GroupUserEntity;

@Entity
@Data
public class WorkMemberEntity {
    @EmbeddedId
    private WorkMemberId workMemberId;

    @MapsId("work")
    @ManyToOne(optional = false)
    @JoinColumn(name = "h_id")
    private WorkEntity work;

    @MapsId("user")
    @ManyToOne(optional = false)
    @JoinColumn(name = "u_id", nullable = false)
    private GroupUserEntity user;

    @Column(name = "is_submit")
    private boolean isSubmited = false;

    @Column(name = "is_late")
    private boolean isLate = false;

    public static WorkMemberEntity makeWorkMemberEntity(String userId, String workId) {
        WorkMemberEntity workMemberEntity = new WorkMemberEntity();
        workMemberEntity.setWorkMemberId(new WorkMemberId(workId, userId));
        return workMemberEntity;
    }

}

