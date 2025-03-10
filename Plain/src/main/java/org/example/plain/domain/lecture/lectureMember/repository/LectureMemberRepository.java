package org.example.plain.domain.lecture.lectureMember.repository;

import org.example.plain.domain.lecture.lectureMember.entity.LectureMember;
import org.example.plain.domain.lecture.normal.entity.Lecture;
import org.example.plain.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LectureMemberRepository extends JpaRepository<LectureMember,String> {

    Optional<List<LectureMember>> findByLectureId(Lecture lecture);

    @Query("SELECT lm.userId " +
            "FROM LectureMember lm " +
            "WHERE lm.id = :lecture")
    Optional<List<User>> findByLectureId(String lecture);

    @Query("SELECT lm.userId " +
            "FROM LectureMember lm " +
            "JOIN lm.userId " +
            "JOIN lm.lectureId " +
            "WHERE lm.userId.id = :userId AND lm.lectureId = :lectureId")
    Optional<User> findByLectureIdAndUserId(String lectureId, String userId);

    void removeByLectureId(Lecture lecture);
}
