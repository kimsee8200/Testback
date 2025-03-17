package org.example.plain.domain.classLecture.dto;

import lombok.Builder;
import org.example.plain.domain.classLecture.entity.ClassLecture;
import org.example.plain.common.enums.ClassType;
import org.example.plain.domain.user.entity.User;


@Builder
public record ClassAddRequest(
        String title,
        String classImg,
        Long maxMember,
        Long price,
        ClassType classType,
        String description
) {
        public ClassLecture toEntity(User user,String code) {
            return ClassLecture.builder()
                    .price(price)
                    .classType(classType)
                    .maxMember(maxMember)
                    .instructor(user)
                    .title(title)
                    .description(description)
                    .code(code)
                    .build();
        }
}
