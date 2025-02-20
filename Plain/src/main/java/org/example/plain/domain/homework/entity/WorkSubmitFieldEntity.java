package org.example.plain.domain.homework.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.example.plain.domain.homework.dto.WorkSubmitField;
import org.example.plain.domain.user.entity.UserEntity;

import java.util.List;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public class WorkSubmitFieldEntity {

    @EmbeddedId
    private WorkSubmitFieldId workSubmitFieldId;

    @MapsId("user")
    @OneToOne
    @JoinColumn(name = "u_id", referencedColumnName = "u_id")
    UserEntity userId;

    @MapsId("work")
    @OneToOne
    @JoinColumn(name = "h_id",referencedColumnName = "h_id")
    WorkEntity workId;

    @OneToMany
    List<FileEntity> fileEntities;

    public static WorkSubmitFieldEntity createEntity(WorkSubmitField workSubmitField) {
        WorkSubmitFieldEntity workSubmitFieldEntity = new WorkSubmitFieldEntity();
        workSubmitFieldEntity.workSubmitFieldId = new WorkSubmitFieldId(workSubmitField.getUserId(), workSubmitField.getWorkId());
        return workSubmitFieldEntity;
    }
}

