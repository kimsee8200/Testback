package org.example.plain.repository;

import org.example.plain.domain.board.dto.Board;
import org.example.plain.domain.board.entity.BoardEntity;
import org.example.plain.domain.homework.dto.Work;
import org.example.plain.domain.homework.entity.WorkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, String> {
    WorkEntity findByWorkId(String id);

    List<WorkEntity> findByGroupId(String  groupId);

    List<BoardEntity> findAllByGroupId(String groupId);

    BoardEntity findByBoardId(String id);

}
