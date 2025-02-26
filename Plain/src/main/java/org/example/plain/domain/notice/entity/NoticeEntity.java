package org.example.plain.domain.notice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "notice")
public class NoticeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "notice_id", unique = true, nullable = false)
    private String noticeId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "user_id", insertable = false, updatable = false)
    private String userId;

    @Column(name = "create_date")
    @CreatedDate
    private LocalDateTime createDate;

    public void setTitle(String title) {
        if (title != null) {
            this.title = title;
        }
    }

    public void setContent(String content) {
        if (content != null) {
            this.content = content;
        }
    }

    public void setUserId(String userId) {
        if (userId != null) {
            this.userId = userId;
        }
    }

    public void setCreateDate(LocalDateTime createDate) {
        if (createDate != null) {
            this.createDate = createDate;
        }
    }
}
