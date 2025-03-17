package org.example.plain.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.plain.domain.board.entity.BoardEntity;


import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Board {
    private String boardId;
    private String groupId;
    private String userId;
    private String type;
    private String title;
    private String content;
    private LocalDateTime createTime;

    public static Board changeBoard(BoardEntity boardEntity) {
        Board board = new Board();
        board.setBoardId(boardEntity.getBoardId());
        board.setGroupId(boardEntity.getGroupId());
        board.setUserId(boardEntity.getUser().getId());
        board.setType(boardEntity.getType());
        board.setTitle(boardEntity.getTitle());
        board.setContent(boardEntity.getContent());
        board.setCreateTime(boardEntity.getCreateDate());
        return board;
    }
}
