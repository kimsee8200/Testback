package org.example.plain.domain.classLecture.dto;

import lombok.Builder;

@Builder
public record ClassResponse(
        Long id,
        String title,
        String description,
        String code
) {
}
