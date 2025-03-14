package org.example.plain.domain.classLecture.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
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
public class InviteController {

    private final ClassInviteService classInviteService;

    @PostMapping("/{classId}/invite-member")
    public ResponseEntity<String> joinCode(
            @PathVariable String classId
    ) {
        return ResponseEntity.ok()
                .body(classInviteService.joinByCode(classId));
    }
}
