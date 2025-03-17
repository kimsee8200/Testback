package org.example.plain.domain.lecture.normal.service;



import lombok.RequiredArgsConstructor;
import org.example.plain.domain.lecture.lectureMember.entity.LectureMember;
import org.example.plain.domain.lecture.lectureMember.repository.LectureMemberRepository;
import org.example.plain.domain.lecture.normal.data.LectureResponse;
import org.example.plain.domain.lecture.normal.entity.Lecture;
import org.example.plain.domain.lecture.normal.interfaces.LectureService;
import org.example.plain.domain.lecture.normal.repository.LectureRepository;
import org.example.plain.domain.user.dto.UserRequest;
import org.example.plain.domain.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureServiceImpl implements LectureService {

    private final LectureMemberRepository lectureMemberRepository;
    private final LectureRepository lectureRepository;

    @Override
    @Transactional(readOnly = true)
    public Lecture detailsOfLecture(String userId, String lectureId) {

        lectureRepository.findById(lectureId);
        lectureMemberRepository.findByLectureIdAndUserId(lectureId, userId).orElseThrow(IllegalCallerException::new);



        return null;
    }

    public LectureResponse detailsOfLecture(String lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId).orElse(null);

        return null;
    }
}
