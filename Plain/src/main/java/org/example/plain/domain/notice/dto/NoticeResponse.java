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
public class NoticeResponse{

    private Long noticeId;
    private String title;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime modifiedAt;
    private User user;

    public NoticeResponse() {

    }

    public static NoticeResponse from(NoticeEntity noticeEntity) {
        NoticeResponse response = new NoticeResponse();
        response.noticeId = noticeEntity.getNoticeId();
        response.title = noticeEntity.getTitle();
        response.content = noticeEntity.getContent();
        response.user = noticeEntity.getUser();
        response.createDate = noticeEntity.getCreateDate();
        response.modifiedAt = noticeEntity.getModifiedAt();
        return response;
    }

}
