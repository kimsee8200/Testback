package org.example.plain.domain.notice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.example.plain.domain.notice.entity.NoticeEntity;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
public class NoticeResponse{

    private String noticeId;
    private String title;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime modifiedAt;
    private String userId;

    public static NoticeResponse from(NoticeEntity noticeEntity) {
        NoticeResponse response = new NoticeResponse();
        response.noticeId = noticeEntity.getNoticeId();
        response.title = noticeEntity.getTitle();
        response.content = noticeEntity.getContent();
        response.userId = noticeEntity.getUser().getId();
        response.createDate = noticeEntity.getCreateDate();
        response.modifiedAt = noticeEntity.getModifiedAt();
        return response;
    }

}
