package org.example.plain.domain.board.dao;

import org.example.plain.domain.board.dto.Board;
import org.example.plain.domain.board.entity.BoardEntity;
import org.example.plain.domain.homework.dto.Work;
import org.example.plain.domain.homework.entity.WorkEntity;
import org.example.plain.repository.BoardRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BoardDaoImpl implements BoardDao {

    private final BoardRepository boardRepository;

    public BoardDaoImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public List<BoardEntity> findAllByGroupId(String groupId) {
        return boardRepository.findAllByGroupId(groupId);
    }


    @Override
    public List<BoardEntity> findAll() {
        return List.of();
    }

    @Override
    public BoardEntity findById(String id) {
        return boardRepository.findByBoardId(id).orElseThrow();
    }

    @Override
    public void save(BoardEntity board) {
        boardRepository.save(board);
    }

    @Override
    public void update(BoardEntity board) {
        boardRepository.save(board);
    }

    @Override
    public void delete(BoardEntity board) {
        boardRepository.delete(board);
    }
}

