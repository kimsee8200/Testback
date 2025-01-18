package org.example.plain.domain.homework.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.example.plain.domain.group.entity.GroupUserEntity;
import org.example.plain.domain.homework.dto.Work;
import org.example.plain.domain.user.User;
import org.example.plain.domain.user.entity.UserEntity;

import java.io.Serializable;

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

@Embeddable
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
class WorkMemberId implements Serializable {
    private String work;
    private String user;

    public WorkMemberId(String work, String user) {
        this.work = work;
        this.user = user;
    }
}