package org.example.plain.domain.notice.dto;

import lombok.Builder;

@Builder
public record NoticeResponse(
        String noticeId,
        String title,
        String content,
        String createDate,
        String userId
) {


}
