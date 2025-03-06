package org.example.plain.domain.notice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.example.plain.domain.notice.entity.NoticeEntity;
import org.example.plain.domain.user.entity.User;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
public class NoticeCommentResponse {

    private Long noticeId;
    private Long commentId;
    private String title;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime modifiedAt;
    private User user;

    public NoticeCommentResponse() {

    }

    public static NoticeCommentResponse from(NoticeEntity noticeEntity) {
        NoticeCommentResponse response = new NoticeCommentResponse();
        response.noticeId = noticeEntity.getNoticeId();
        response.commentId = noticeEntity.getNoticeId();
        response.title = noticeEntity.getTitle();
        response.content = noticeEntity.getContent();
        response.user = noticeEntity.getUser();
        response.createDate = noticeEntity.getCreateDate();
        response.modifiedAt = noticeEntity.getModifiedAt();
        return response;
    }

}
