package org.example.plain.domain.homework.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.plain.domain.board.entity.BoardEntity;
import org.example.plain.domain.homework.dto.Work;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@DiscriminatorValue("Work")
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "homework")
public class WorkEntity extends BoardEntity {

    @Column(name = "h_id",unique = true,nullable = false)
    private String workId;

    @Column(name = "submit_date", nullable = false)
    private LocalDateTime deadline;

    public static WorkEntity workToWorkEntity(Work work) {
        WorkEntity workEntity = new WorkEntity();
        workEntity.setBoardId(work.getBoardId());
        workEntity.setGroupId(work.getGroupId());
        workEntity.setWorkId(work.getWorkId());
        workEntity.setTitle(work.getTitle());
        workEntity.setContent(work.getContent());
        workEntity.setType(work.getType());
        workEntity.setDeadline(work.getDeadline());
        return workEntity;
    }

    public static WorkEntity chaingeWorkEntitytoWork(Work work, WorkEntity workEntity) {
        WorkEntity chaingeWorkEntity = workEntity;
        workEntity.setTitle(work.getTitle());
        workEntity.setContent(work.getContent());
        workEntity.setType(work.getType());
        workEntity.setDeadline(work.getDeadline());
        return chaingeWorkEntity;
    }

    public void setWorkId(String workId) {
        if (workId != null) {
            this.workId = workId;
        }
    }

    public void setDeadline(LocalDateTime deadline) {
        if (deadline != null) {
            this.deadline = deadline;
        }
    }
}
