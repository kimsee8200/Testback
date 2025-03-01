package org.example.plain.repository;

import org.example.plain.domain.groupmember.entity.ClassMember;
import org.example.plain.domain.homework.entity.WorkEntity;
import org.example.plain.domain.homework.entity.WorkMemberEntity;
import org.example.plain.domain.homework.entity.WorkMemberId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkMemberRepository extends JpaRepository<WorkMemberEntity, WorkMemberId> {
    List<WorkMemberEntity> findByUser(ClassMember groupUser);

    List<WorkMemberEntity> findByWork(WorkEntity workId);
}
