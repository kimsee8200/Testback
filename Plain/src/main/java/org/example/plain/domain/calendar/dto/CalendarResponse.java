package org.example.plain.domain.calendar.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.example.plain.domain.calendar.entity.CalendarEntity;
import org.example.plain.domain.user.entity.User;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
public class CalendarResponse {

    private Long calId;
    private String title;
    private String content;
    private LocalDateTime dateInfo;
    private User user;

    public CalendarResponse() {

    }

    public static CalendarResponse from(CalendarEntity calendarEntity) {
        CalendarResponse response = new CalendarResponse();
        response.calId = calendarEntity.getCalId();
        response.title = calendarEntity.getTitle();
        response.content = calendarEntity.getContent();
        response.user = calendarEntity.getUser();
        response.dateInfo = calendarEntity.getDateInfo();
        return response;
    }

}
