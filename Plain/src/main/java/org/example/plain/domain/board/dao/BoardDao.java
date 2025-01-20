package org.example.plain.domain.board.dao;

import org.example.plain.domain.Dao;
import org.example.plain.domain.board.dto.Board;
import org.example.plain.domain.board.entity.BoardEntity;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface BoardDao extends Dao<BoardEntity> {
    List<BoardEntity> findAllByGroupId(String groupId);
}
