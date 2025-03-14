package org.example.plain.domain.classLecture.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.example.plain.common.config.SecurityUtils;
import org.example.plain.domain.classLecture.service.ClassInviteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "ClassInvite controller api", description = "클래스 초대 API")
@RestController
@AllArgsConstructor
@RequestMapping("/classes")
public class ClassInviteController {

    private final ClassInviteService classInviteService;

    @Operation(summary = "클래스 이메일 초대")
    @PostMapping("/{classId}/invite-member")
    public ResponseEntity<String> joinCode(
            @PathVariable String classId
    ) {
        return ResponseEntity.ok()
                .body(classInviteService.joinByCode(classId));
    }

    @Operation(summary = "클래스 가입")
    @PostMapping("/{classId}/join")
    public ResponseEntity<String> joinClass(
            @PathVariable String classId
    ) {
        String userId = SecurityUtils.getUserId();
        classInviteService.joinClass(userId, classId);
        return ResponseEntity.ok("클래스 가입 완료");
    }
}

