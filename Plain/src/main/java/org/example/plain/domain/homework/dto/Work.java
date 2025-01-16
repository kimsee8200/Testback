package org.example.plain.domain.homework.dto;

import lombok.Data;
import org.example.plain.domain.board.dto.Board;

import java.time.LocalDateTime;

@Data
public class Work extends Board {
    private LocalDateTime deadline;

    public Work(String id, String title, String content, LocalDateTime deadline) {
        super(id,title, content);
        this.deadline = deadline;
    }
}
