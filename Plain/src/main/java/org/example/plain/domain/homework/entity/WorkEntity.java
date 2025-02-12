package org.example.plain.domain.homework.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.plain.domain.board.entity.BoardEntity;
import org.example.plain.domain.homework.dto.Work;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@DiscriminatorValue("work")
public class WorkEntity extends BoardEntity {

    @Column(name = "h_id")
    private String workId;

    @Column(name = "submit_date", nullable = false)
    private LocalDateTime deadline;

    public static WorkEntity workToWorkEntity(Work work) {
        WorkEntity workEntity = new WorkEntity();
        work.setBoardId(work.getBoardId());
        work.setGroupId(work.getGroupId());
        workEntity.setWorkId(work.getWorkId());
        workEntity.setTitle(work.getTitle());
        workEntity.setContent(work.getContent());
        workEntity.setType(work.getType());
        workEntity.setDeadline(work.getDeadline());
        return workEntity;
    }
}
