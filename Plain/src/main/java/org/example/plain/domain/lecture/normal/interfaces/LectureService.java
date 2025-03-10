package org.example.plain.domain.lecture.normal.interfaces;


import org.example.plain.domain.lecture.normal.data.LectureResponse;
import org.example.plain.domain.lecture.normal.entity.Lecture;
import org.example.plain.domain.user.dto.UserRequest;

import java.util.List;

public interface LectureService {
    public Lecture detailsOfLecture(String userId, String lectureId);
    public LectureResponse detailsOfLecture(String lectureId);
}
