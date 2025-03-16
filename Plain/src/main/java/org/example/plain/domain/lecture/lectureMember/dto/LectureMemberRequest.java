package org.example.plain.domain.lecture.lectureMember.dto;

import lombok.Builder;
import org.example.plain.domain.lecture.lectureMember.entity.LectureMember;


public record LectureMemberRequest(
        String userId,
        String lectureId
) {
}
