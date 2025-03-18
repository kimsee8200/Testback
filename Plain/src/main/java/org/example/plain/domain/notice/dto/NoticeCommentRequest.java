package org.example.plain.domain.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.plain.domain.user.entity.User;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NoticeCommentRequest {

    private Long commentId;
    private Long noticeId;
    private String title;
    private String content;
    private User user;

}
