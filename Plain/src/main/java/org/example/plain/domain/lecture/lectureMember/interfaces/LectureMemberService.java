package org.example.plain.domain.lecture.lectureMember.interfaces;

import org.example.plain.domain.user.dto.UserResponse;

import java.util.List;

public interface LectureMemberService {
    public List<UserResponse> getLectureStudents(String teacher, String lectureId);
    public boolean addLectureStudent(String lectureId, String studentId);
    public boolean removeLectureStudents(String teacher, String studentId) throws IllegalAccessException;
}
