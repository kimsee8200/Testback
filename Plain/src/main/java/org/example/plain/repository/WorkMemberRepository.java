package org.example.plain.repository;

import org.example.plain.domain.group.entity.GroupUserEntity;
import org.example.plain.domain.homework.entity.WorkMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkMemberRepository extends JpaRepository<WorkMemberEntity, String> {
    List<WorkMemberEntity> findByUser(GroupUserEntity groupUser);

    List<WorkMemberEntity> findByWorkId(String workId);
}
