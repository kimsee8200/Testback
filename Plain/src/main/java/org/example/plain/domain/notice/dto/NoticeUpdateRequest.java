package org.example.plain.domain.notice.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class NoticeUpdateRequest {

    private Long noticeId;
    private String title;
    private String content;

}
