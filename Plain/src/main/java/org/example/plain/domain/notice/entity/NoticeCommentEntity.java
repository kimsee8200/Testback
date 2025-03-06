package org.example.plain.domain.notice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.example.plain.domain.user.entity.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "notice_comment")
public class NoticeCommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", unique = true, nullable = false)
    // jpa에서 작동되지 않는다고하여 넣음
    private Long commentId;

    @Column(name = "notice_id", unique = true, nullable = false)
    // jpa에서 작동되지 않는다고하여 넣음
    private Long noticeId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Column(name = "create_date")
    @CreatedDate
    private LocalDateTime createDate;

    @Column(name = "modified_at")
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    private static final AtomicLong counter = new AtomicLong();

    public static NoticeCommentEntity create(String title, String content,Long noticeId){
        NoticeCommentEntity  noticeCommentEntity = new NoticeCommentEntity();
        noticeCommentEntity.commentId = counter.incrementAndGet();
        noticeCommentEntity.noticeId = noticeId;
        noticeCommentEntity.title = title;
        noticeCommentEntity.content = content;
        noticeCommentEntity.user = user;
        noticeCommentEntity.createDate = LocalDateTime.now();
        noticeCommentEntity.modifiedAt = LocalDateTime.now();
        return noticeCommentEntity;
    }

    public void  update(Long commentId, String title, String content, Long noticeId) {
        this.commentId = commentId;
        this.noticeId = noticeId;
        this.title = title;
        this.content = content;
        modifiedAt = LocalDateTime.now();
    }

}
