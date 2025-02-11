package org.example.plain.domain.classLecture.dto;

import lombok.Builder;
import org.example.plain.domain.classLecture.entity.ClassLecture;
import org.example.plain.domain.user.entity.User;


@Builder
public record ClassAddRequest(
        Long id,
        User user,
        String title,
        String description
) {
        public ClassLecture toEntity(User user,String code) {
            return ClassLecture.builder()
                    .id(id)
                    .user(user)
                    .title(title)
                    .description(description)
                    .code(code)
                    .build();
        }
}
