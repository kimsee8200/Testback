package org.example.plain.domain.notice.dto;

import lombok.Getter;
import lombok.ToString;
import org.example.plain.domain.user.entity.User;

@Getter
@ToString
public class NoticeUpdateRequest {

    private Long noticeId;
    private String title;
    private String content;
    private User user;

}
