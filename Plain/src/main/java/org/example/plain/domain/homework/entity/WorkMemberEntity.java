package org.example.plain.domain.homework.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.example.plain.domain.groupmember.entity.GroupMember;
import org.example.plain.domain.user.entity.User;

@Entity
@Data
@Table(name = "homework_member")
public class WorkMemberEntity {
    @EmbeddedId
    private WorkMemberId workMemberId;

    @MapsId("work")
    @ManyToOne(optional = false)
    @JoinColumn(name = "h_id")
    private WorkEntity work;

    @MapsId("memberUser")
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "user_id")
    private User user;

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

