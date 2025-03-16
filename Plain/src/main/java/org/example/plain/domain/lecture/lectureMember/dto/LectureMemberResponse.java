package org.example.plain.domain.lecture.lectureMember.dto;

import lombok.Builder;
import org.example.plain.domain.lecture.lectureMember.entity.LectureMember;

@Builder
public record LectureMemberResponse (
         String username,
         String lectureId,
         String cord
) {
    public LectureMemberResponse chaingeLectureMember(LectureMember lectureMember) {
        return LectureMemberResponse.builder()
                .username(lectureMember.getUserId().getUsername())
                .lectureId(lectureMember.getLectureId().getLectureId()).build();
    }
}
