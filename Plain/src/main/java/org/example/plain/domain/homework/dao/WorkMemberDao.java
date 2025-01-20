package org.example.plain.domain.homework.dao;

import org.example.plain.domain.Dao;
import org.example.plain.domain.homework.dto.WorkMember;
import org.example.plain.domain.homework.entity.WorkMemberEntity;

import java.util.List;

public interface WorkMemberDao{
    List<WorkMemberEntity> findByWorkId(String workId);

    List<WorkMemberEntity> findAll();

    WorkMemberEntity findById(String id);

    void save(WorkMemberEntity workMemberEntity);

    void delete(WorkMemberEntity workMemberEntity);
}
