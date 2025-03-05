package org.example.plain.domain.notice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.example.plain.domain.user.entity.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "notice")
public class NoticeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public static NoticeEntity create(String title, String content,User user){
        NoticeEntity noticeEntity  = new NoticeEntity();
        noticeEntity.noticeId = counter.incrementAndGet();
        noticeEntity.title = title;
        noticeEntity.content = content;
        noticeEntity.user = user;
        noticeEntity.createDate = LocalDateTime.now();
        noticeEntity.modifiedAt = LocalDateTime.now();
        return noticeEntity;
    }

    public void update(Long noticeId, String title, String content, User user) {
        this.noticeId = noticeId;
        this.title = title;
        this.content = content;
        this.user = user;
        modifiedAt = LocalDateTime.now();
    }


}
