package org.example.plain.domain.classLecture.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.example.plain.common.config.SecurityUtils;
import org.example.plain.domain.classLecture.service.ClassJoinService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "ClassInvite controller api", description = "클래스 초대 API")
@RestController
@AllArgsConstructor
@RequestMapping("/classes")
public class ClassJoinController {

    private final ClassJoinService classJoinService;


    @Operation(summary = "클래스 코드 가입")
    @PostMapping("/join/{code}")
    public ResponseEntity<String> joinCode(
            @PathVariable String code
    ) {
        String userId = SecurityUtils.getUserId();
        return ResponseEntity.ok().body(classJoinService.joinByCode(code, userId));
    }

    @Operation(summary = "클래스 버튼 가입")
    @PostMapping("/{classId}/join")
    public ResponseEntity<String> joinClass(
            @PathVariable String classId
    ) {
        String userId = SecurityUtils.getUserId();
        classJoinService.joinClass(userId, classId);
        return ResponseEntity.ok("클래스 가입 완료");
    }
}

