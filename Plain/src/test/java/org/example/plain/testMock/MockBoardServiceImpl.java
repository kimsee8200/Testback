package org.example.plain.testMock;

import org.example.plain.domain.board.BoardService;
import org.example.plain.domain.board.dto.Board;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MockBoardServiceImpl implements BoardService {
    @Override
    public Board getBoard(String id) {
        return null;
    }

    @Override
    public void createBoard(Board board) {

    }

    @Override
    public void updateBoard(Board board, String boardId, String userId) {

    }

    @Override
    public void deleteBoard(String boardId, String userId) {

    }


    @Override
    public List<Board> getGroupBoards(String groupId) {
        return List.of();
    }
}
