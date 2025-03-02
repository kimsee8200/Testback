package org.example.plain.domain.classLecture.dto;

import lombok.Builder;
import org.example.plain.domain.classLecture.entity.ClassLecture;

@Builder
public record ClassResponse(
        String id,
        String title,
        String description,
        String code
) {
    public static ClassResponse chaingeClassLectureToResponse(ClassLecture classLecture) {
        ClassResponse classResponse = new ClassResponse(classLecture.getId(), classLecture.getTitle(), classLecture.getDescription(), classLecture.getCode());
        return classResponse;
    }
}
