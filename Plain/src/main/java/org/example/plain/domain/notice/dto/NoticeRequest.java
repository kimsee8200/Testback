package org.example.plain.domain.notice.dto;

import lombok.*;
import org.example.plain.domain.notice.entity.NoticeEntity;
import org.example.plain.domain.user.entity.User;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NoticeRequest{

    private Long id;
    private String title;
    private String content;
    private User user;

}
