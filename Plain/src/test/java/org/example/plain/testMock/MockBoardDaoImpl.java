package org.example.plain.testMock;

import org.example.plain.domain.board.BoardService;
import org.example.plain.domain.board.dao.BoardDao;
import org.example.plain.domain.board.dto.Board;
import org.example.plain.domain.board.entity.BoardEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MockBoardDaoImpl implements BoardDao {


    @Override
    public List<BoardEntity> findAllByGroupId(String groupId) {
        return List.of();
    }

    @Override
    public List<BoardEntity> findAll() {
        return List.of();
    }

    @Override
    public BoardEntity findById(String id) {
        return null;
    }

    @Override
    public void save(BoardEntity boardEntity) {

    }

    @Override
    public void update(BoardEntity boardEntity) {

    }

    @Override
    public void delete(BoardEntity boardEntity) {

    }
}
