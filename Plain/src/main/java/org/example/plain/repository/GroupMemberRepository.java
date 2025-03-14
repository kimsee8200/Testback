package org.example.plain.repository;

import jakarta.transaction.Transactional;
import org.example.plain.domain.classLecture.entity.ClassLecture;
import org.example.plain.domain.classMember.entity.ClassMember;
import org.example.plain.domain.classMember.entity.ClassMemberId;
import org.example.plain.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupMemberRepository extends JpaRepository<ClassMember, ClassMemberId> {
    ClassMember findByClassLectureAndUser(ClassLecture group, User user);
    List<ClassMember> findAllByClassLecture(ClassLecture group);
    @Transactional
    void deleteByClassLectureAndUser(ClassLecture group, User user);
}
