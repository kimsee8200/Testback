package org.example.plain.domain.homework.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.example.plain.domain.file.entity.FileEntity;
import org.example.plain.domain.user.entity.User;

import java.util.List;

@Entity
@Data
@Table(name = "homework_member")
public class WorkMemberEntity {
    @EmbeddedId
    private WorkMemberId workMemberId;

    @MapsId("work")
    @ManyToOne
    @JoinColumn(name = "h_id")
    private WorkEntity work;

    @MapsId("memberUser")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "is_submit")
    private boolean isSubmited = false;

    @Column(name = "is_late")
    private boolean isLate = false;

    @OneToMany
    private List<FileEntity> fileEntities;

    public static WorkMemberEntity makeWorkMemberEntity(User userId, WorkEntity workId) {
        WorkMemberEntity workMemberEntity = new WorkMemberEntity();
        workMemberEntity.setWorkMemberId(new WorkMemberId(workId.getWorkId(), userId.getId()));
        workMemberEntity.setWork(workId);
        workMemberEntity.setUser(userId);
        return workMemberEntity;
    }

}

