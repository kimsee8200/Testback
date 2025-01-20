package org.example.plain.repository;

import org.example.plain.domain.homework.entity.WorkSubmitFieldEntity;
import org.example.plain.domain.homework.entity.WorkSubmitFieldId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkSubmitFieldRepository extends JpaRepository<WorkSubmitFieldEntity, WorkSubmitFieldId> {
    List<WorkSubmitFieldEntity> findByWorkId(String workId);
}
