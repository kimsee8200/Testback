package org.example.plain.domain.calendar.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.example.plain.common.enums.Category;
import org.example.plain.domain.user.entity.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Calender")
public class CalendarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id", unique = true, nullable = false)
    private Long calId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "date_info")
    @CreatedDate
    private LocalDateTime dateInfo;

    private static final AtomicLong counter = new AtomicLong();

    public static CalendarEntity create(String title, String content, Category category, User user){
        CalendarEntity calendarEntity  = new CalendarEntity();
        calendarEntity.calId = counter.incrementAndGet();
        calendarEntity.title = title;
        calendarEntity.category = category;
        calendarEntity.content = content;
        calendarEntity.user = user;
        calendarEntity.dateInfo = LocalDateTime.now();
        return calendarEntity;
    }

    public void  update(Long calId, String title, String content) {
        this.calId = calId;
        this.title = title;
        this.content = content;
        dateInfo = LocalDateTime.now();
    }


}
