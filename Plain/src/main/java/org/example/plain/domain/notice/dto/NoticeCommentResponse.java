package org.example.plain.domain.notice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.example.plain.domain.notice.entity.NoticeCommentEntity;
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

    public static NoticeCommentResponse from(NoticeCommentEntity noticeCommentEntity) {
        NoticeCommentResponse response = new NoticeCommentResponse();
        response.noticeId = noticeCommentEntity.getNoticeId();
        response.commentId = noticeCommentEntity.getNoticeId();
        response.title = noticeCommentEntity.getTitle();
        response.content = noticeCommentEntity.getContent();
        response.user = noticeCommentEntity.getUser();
        response.createDate = noticeCommentEntity.getCreateDate();
        response.modifiedAt = noticeCommentEntity.getModifiedAt();
        return response;
    }

}
