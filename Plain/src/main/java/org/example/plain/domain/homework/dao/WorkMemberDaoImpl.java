package org.example.plain.domain.homework.dao;

import org.example.plain.domain.homework.entity.WorkMemberEntity;
import org.example.plain.repository.WorkMemberRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WorkMemberDaoImpl implements WorkMemberDao {
    private WorkMemberRepository workMemberRepository;

    public WorkMemberDaoImpl(WorkMemberRepository workMemberRepository) {
        this.workMemberRepository = workMemberRepository;
    }

    @Override
    public List<WorkMemberEntity> findByWorkId(String workId) {
        return workMemberRepository.findByWorkId(workId);
    }

    @Override
    public List<WorkMemberEntity> findAll() {
        return List.of();
    }

    @Override
    public WorkMemberEntity findById(String id) {
        return null;
    }

    @Override
    public void save(WorkMemberEntity workMemberEntity) {
        workMemberRepository.save(workMemberEntity);
    }


    @Override
    public void delete(WorkMemberEntity workMemberEntity) {
        workMemberRepository.delete(workMemberEntity);
    }
}
