package org.example.plain.domain.calendar.service;

import lombok.RequiredArgsConstructor;
import org.example.plain.common.ResponseBody;
import org.example.plain.common.enums.Message;
import org.example.plain.domain.calendar.dto.CalendarRequest;
import org.example.plain.domain.calendar.dto.CalendarResponse;
import org.example.plain.domain.calendar.entity.CalendarEntity;
import org.example.plain.domain.calendar.repository.CalendarRepository;
import org.example.plain.domain.user.entity.User;
import org.example.plain.domain.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final UserRepository userRepository;
    private final CalendarRepository calendarRepository;

    @Transactional
    public ResponseBody<CalendarResponse> insertCalendar(CalendarRequest calendarRequest) {
        // User ID로 User 조회
        User user = userRepository.findById(calendarRequest.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        // NoticeEntity 생성 (User 포함)
        CalendarEntity insertCalendar = CalendarEntity.create(
                calendarRequest.getTitle(),
                calendarRequest.getTitle(),
                user
        );

        // 저장
        CalendarEntity calendarEntity = calendarRepository.save(insertCalendar);

        // NoticeResponse 생성 및 반환
        return new ResponseBody<>(Message.OK.name(), HttpStatus.OK, CalendarResponse.from(calendarEntity));
    }

    @Transactional
    public ResponseBody<CalendarResponse> updateCalendar(CalendarRequest calendarRequest) {

        CalendarEntity calendarEntity = calendarRepository.findById(calendarRequest.getCalId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        calendarEntity.update(
                calendarRequest.getCalId(),
                calendarRequest.getTitle(),
                calendarRequest.getContent()

        );

        return new ResponseBody<>(Message.OK.name(), HttpStatus.OK, CalendarResponse.from(calendarEntity));
    }

    public ResponseBody<CalendarResponse> getDetailCalendar(Long calId){
        return new ResponseBody<>(Message.OK.name(), HttpStatus.OK, CalendarResponse.from(calendarRepository.findById(calId).orElseThrow()));
    }

    @Transactional
    public void deleteCalendar(Long calId) {
        CalendarEntity calendarEntity = calendarRepository.findById(calId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        calendarRepository.delete(calendarEntity);
    }

}
