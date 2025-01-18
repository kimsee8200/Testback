package org.example.plain.domain.board;

import org.example.plain.domain.board.dto.Board;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BoardService {
    public Board getBoard(String id);
    public void createBoard(Board board);
    public void updateBoard(Board board, String boardId, String userId);
    public void deleteBoard(String boardId, String userId);
    public List<Board> getGroupBoards(String groupId);
}
