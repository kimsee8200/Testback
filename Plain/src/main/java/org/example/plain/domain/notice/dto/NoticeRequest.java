package org.example.plain.domain.notice.dto;

import lombok.Builder;
import org.example.plain.domain.notice.entity.NoticeEntity;
import org.example.plain.domain.user.entity.User;

@Builder
public record NoticeRequest(String userId,
                            String title,
                            String content) {

    public NoticeEntity toEntity(User instructor) {
        return NoticeEntity.builder()
                .instructor(instructor) // User 객체 (instructor) 전달
                .title(title)           // Title 필드 매핑
                .content(content)       // Content 필드 매핑
                .build();
    }
}
