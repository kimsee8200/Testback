package org.example.plain.domain.classLecture.controller;

import lombok.RequiredArgsConstructor;
import org.example.plain.domain.classLecture.dto.ClassAddRequest;
import org.example.plain.domain.classLecture.dto.ClassRequest;
import org.example.plain.domain.classLecture.dto.ClassResponse;
import org.example.plain.domain.classLecture.service.ClassInviteService;
import org.example.plain.domain.classLecture.service.ClassLectureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/classes")
public class ClassLectureController {

    private final ClassLectureService classLectureService;
    private final ClassInviteService classInviteService;

    @GetMapping
    public ResponseEntity<List<ClassResponse>> getAllClass(){
        return ResponseEntity.ok()
                .body(classLectureService.getAllClass());
    }

    @GetMapping("/{classId}")
    public ResponseEntity<ClassResponse> getClass(
            @PathVariable Long classId
    ) {
        return ResponseEntity.ok()
                .body(classLectureService.getClass(classId));
    }

    @PostMapping
    public ResponseEntity<ClassResponse> createClass(
            @RequestBody ClassAddRequest classAddRequest
    ) {
        return ResponseEntity.ok()
                .body(classLectureService.createClass(classAddRequest));
    }

    @PatchMapping("/{classId}")
    public ResponseEntity<ClassResponse> updateClass(
            @PathVariable Long classId,
            @RequestHeader(value = "userId") Long userId,
            @RequestBody ClassRequest classRequest
    ) {
        return ResponseEntity.ok()
                .body(classLectureService.modifiedClass(userId, classId, classRequest));
    }

    @DeleteMapping("/{classId}")
    public ResponseEntity<ClassResponse> deleteClass(
            @PathVariable Long classId,
            @RequestHeader(value = "userId") Long userId
    ) {
        ClassResponse deleteClass = classLectureService.deleteClass(userId, classId);
        return ResponseEntity.ok().body(deleteClass);
    }

    @PostMapping("/{classId}/invite-member")
    public ResponseEntity<String> joinCode(
            @PathVariable Long classId
    ) {
        return ResponseEntity.ok()
                .body(classInviteService.joinByCode(classId));
    }

}
