package org.example.plain.domain.lecture.lectureMember.controller;

import lombok.RequiredArgsConstructor;
import org.example.plain.common.ResponseField;
import org.example.plain.common.ResponseMaker;
import org.example.plain.domain.lecture.lectureMember.interfaces.LectureMemberService;
import org.example.plain.domain.lecture.normal.data.LectureResponse;
import org.example.plain.domain.lecture.normal.interfaces.LectureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/lecture")
@RequiredArgsConstructor
public class lectureController {

    private final LectureService lectureService;
    private final LectureMemberService lectureMemberService;

    @GetMapping("/")
    public ResponseEntity<ResponseField<LectureResponse>> getLecture(String lectureId) {
        LectureResponse response = lectureService.detailsOfLecture(lectureId);
        return new ResponseMaker<LectureResponse>().ok(response);
    }
}
