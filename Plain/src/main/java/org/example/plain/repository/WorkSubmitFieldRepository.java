package org.example.plain.repository;

import org.example.plain.domain.homework.entity.WorkEntity;
import org.example.plain.domain.homework.entity.WorkSubmitFieldEntity;
import org.example.plain.domain.homework.entity.WorkSubmitFieldId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkSubmitFieldRepository extends JpaRepository<WorkSubmitFieldEntity, WorkSubmitFieldId> {
    List<WorkSubmitFieldEntity> findByWorkId(WorkEntity workId);
}
