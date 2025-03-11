package org.example.plain.domain.calendar.dto;

import lombok.Getter;
import lombok.ToString;
import org.example.plain.domain.user.entity.User;

@Getter
@ToString
public class CalendarRequest {

    private Long calId;
    private String title;
    private String content;
    private User user;
}
