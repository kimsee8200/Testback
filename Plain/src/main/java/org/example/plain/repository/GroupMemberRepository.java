package org.example.plain.repository;

import jakarta.transaction.Transactional;
import org.example.plain.entity.Group;
import org.example.plain.entity.GroupMember;
import org.example.plain.entity.GroupMemberId;
import org.example.plain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupMemberRepository extends JpaRepository<GroupMember, GroupMemberId> {
    GroupMember findByGroupAndUser(Group group, User user);
    List<GroupMember> findAllByGroup(Group group);
    @Transactional
    void deleteByGroupAndUser(Group group, User user);
}
