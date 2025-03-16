package org.example.plain.domain.lecture.lectureMember.service;

import lombok.RequiredArgsConstructor;
import org.example.plain.domain.lecture.lectureMember.entity.LectureMember;
import org.example.plain.domain.lecture.lectureMember.interfaces.LectureMemberService;
import org.example.plain.domain.lecture.lectureMember.repository.LectureMemberRepository;
import org.example.plain.domain.lecture.normal.entity.Lecture;
import org.example.plain.domain.lecture.normal.repository.LectureRepository;
import org.example.plain.domain.user.dto.UserResponse;
import org.example.plain.domain.user.entity.User;
import org.example.plain.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureMemberImpl implements LectureMemberService {

    private final LectureMemberRepository lectureMemberRepository;
    private final LectureRepository lectureRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public List<UserResponse> getLectureStudents(String teacher, String lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow();
        if (!lecture.getTeacher().getId().equals(teacher)) {
            throw new RuntimeException();
        }
        List<LectureMember> lectureMembers = lectureMemberRepository.findByLectureId(lecture).orElseThrow();

        List<UserResponse> userResponses = new ArrayList<>();

        for (LectureMember lectureMember : lectureMembers) {
            userResponses.add(UserResponse.chaingeUsertoUserResponse(lectureMember.getUserId()));
        }
        // 리팩토리 요망,

        return List.of();
    }

    @Override
    @Transactional
    public boolean addLectureStudent(String lectureId, String studentId) {

        // 구매 검증.

        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow();
        User user = userRepository.findById(studentId).orElseThrow();

        LectureMember lectureMember = LectureMember.chaingeToRequestLectureMember(user,lecture);
        lectureMemberRepository.save(lectureMember);
        return true;
    }


    // 실시간 강의 종료시.
    @Override
    public boolean removeLectureStudents(String teacher, String studentId) throws IllegalAccessException {
        Lecture lecture = lectureRepository.findById(studentId).orElseThrow();
        if (!lecture.getTeacher().getId().equals(teacher)) {
            throw new IllegalAccessException();
        }

        return true;
    }

}
