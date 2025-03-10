package org.example.plain.domain.lecture.normal.data;

public record LectureResponse (
        String lectureId,
        String teacherName,
        String type,
        String title,
        String description,
        Integer price
) {
}
