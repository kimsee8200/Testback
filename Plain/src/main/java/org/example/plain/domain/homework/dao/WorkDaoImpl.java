package org.example.plain.domain.homework.dao;

import org.example.plain.domain.board.entity.BoardEntity;
import org.example.plain.domain.homework.dto.Work;
import org.example.plain.domain.homework.entity.WorkEntity;
import org.example.plain.repository.BoardRepository;
import org.example.plain.repository.WorkMemberRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class WorkDaoImpl implements WorkDao {

    private final BoardRepository boardRepository;
    private final WorkMemberRepository workMemberRepository;
//    private final GroupUserRepository groupUserRepository;

    public WorkDaoImpl(BoardRepository boardRepository, WorkMemberRepository workMemberRepository) {
        this.boardRepository = boardRepository;
        this.workMemberRepository = workMemberRepository;
    }

    private WorkEntity transportWork(Work work) {
        WorkEntity workEntity = new WorkEntity();
        workEntity.setTitle(work.getTitle());
        workEntity.setContent(work.getContent());
        workEntity.setWorkId(work.getWorkId());
        workEntity.setGroupId(work.getGroupId());
        workEntity.setDeadline(work.getDeadline());
        return workEntity;
    }

    private Work transportWorkEntity(WorkEntity workEntity) {
        Work work = new Work();
        work.setBoardId(workEntity.getBoardId());
        work.setGroupId(workEntity.getGroupId());
        work.setWorkId(workEntity.getWorkId());
        work.setTitle(workEntity.getTitle());
        work.setContent(workEntity.getContent());
        work.setDeadline(workEntity.getDeadline());
        return work;
    }

    @Override
    public List<WorkEntity> findAll() {
        return List.of();
    }

    @Override
    public WorkEntity findById(String id) {
        return (WorkEntity) boardRepository.findByBoardId(id);
    }

    @Override
    public void save(WorkEntity workEntity) {
        boardRepository.save(workEntity);
    }

    @Override
    public void update(WorkEntity workEntity) {
        boardRepository.save(workEntity);
    }

    @Override
    public void delete(WorkEntity workEntity) {
        boardRepository.delete(workEntity);
    }

    @Override
    public List<Work> selectAllGroupWork(String groupId) {
        List<Work> works = new ArrayList<>();
        for(BoardEntity entity:boardRepository.findByGroupId(groupId)) {
            if(entity instanceof WorkEntity)
                works.add(transportWorkEntity((WorkEntity) entity));
        }
        return works;
    }

//    public List<Work> selectGroupAndWorkId(String user, String workId, String groupId) {
//        groupUserRepository.findbyGroupAndWorkId();
//        workMemberRepository.findByUser()
//    }
}
