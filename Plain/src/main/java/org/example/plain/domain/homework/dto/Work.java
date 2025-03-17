package org.example.plain.domain.homework.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.plain.domain.board.dto.Board;
import org.example.plain.domain.homework.entity.WorkEntity;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class Work extends Board {
    private String workId;
    private LocalDateTime deadline;

    public Work(){
    }

    public Work(String boardId, String groupId, String writer, String workId, String title, String content, LocalDateTime deadline) {
        super(boardId,groupId,writer,"Work",title,content,deadline);
        this.workId = workId;
        this.deadline = deadline;
    }

    public static Work changeWorkEntity(WorkEntity workEntity){
        Work work = new Work();
        work.setWorkId(workEntity.getWorkId());
        work.setDeadline(workEntity.getDeadline());
        work.setGroupId(workEntity.getClassId());
        work.setBoardId(workEntity.getBoardId());
        work.setUserId(workEntity.getUser().getId());
        work.setTitle(workEntity.getTitle());
        work.setContent(workEntity.getContent());
        work.setType(workEntity.getType());
        return work;
    }

}
