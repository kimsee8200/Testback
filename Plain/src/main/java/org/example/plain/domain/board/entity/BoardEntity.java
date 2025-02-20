package org.example.plain.domain.board.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.example.plain.domain.user.entity.User;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public class BoardEntity {
    @Id
    @Column(name = "b_id", unique = true, nullable = false)
    private String boardId;

    @Column(name = "g_id")
    private String groupId;

    @JoinColumn(name = "u_id", referencedColumnName = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String userId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column
    private int type;

    @Column(name = "create_date")
    @CreatedDate
    private LocalDateTime createDate;

}
