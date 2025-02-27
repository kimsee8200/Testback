package org.example.plain.domain.classLecture.dto;

import lombok.Builder;

@Builder
public record ClassResponse(
        String id,
        String title,
        String description,
        String code
) {
}
