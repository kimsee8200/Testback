package org.example.plain.repository;

import jakarta.transaction.Transactional;
import org.example.plain.domain.classLecture.entity.ClassLecture;
import org.example.plain.domain.groupmember.entity.GroupMember;
import org.example.plain.domain.groupmember.entity.GroupMemberId;
import org.example.plain.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, GroupMemberId> {
    GroupMember findByClassLectureAndUser(ClassLecture group, User user);
    List<GroupMember> findAllByClassLecture(ClassLecture group);
    @Transactional
    void deleteByClassLectureAndUser(ClassLecture group, User user);
}
