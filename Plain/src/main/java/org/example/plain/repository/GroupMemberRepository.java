package org.example.plain.repository;

import jakarta.transaction.Transactional;
import org.example.plain.domain.group.entity.Group;
import org.example.plain.domain.groupmember.entity.GroupMember;
import org.example.plain.domain.groupmember.entity.GroupMemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, GroupMemberId> {
    GroupMember findByGroupAndUser(Group group, User user);
    List<GroupMember> findAllByGroup(Group group);
    @Transactional
    void deleteByGroupAndUser(Group group, User user);
}
