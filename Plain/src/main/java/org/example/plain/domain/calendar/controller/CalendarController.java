package org.example.plain.domain.calendar.controller;

import lombok.RequiredArgsConstructor;
import org.example.plain.common.ResponseBody;
import org.example.plain.domain.calendar.dto.CalendarRequest;
import org.example.plain.domain.calendar.dto.CalendarResponse;
import org.example.plain.domain.calendar.service.CalendarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calendar")
public class CalendarController {

    private final CalendarService calendarService;

    @PostMapping("/{insert}")
    public ResponseEntity<ResponseBody<CalendarResponse>> insertCalendar(
            @RequestBody CalendarRequest calendarRequest) {

        ResponseBody<CalendarResponse> responseBody = calendarService.insertCalendar(calendarRequest);

        return ResponseEntity.status(responseBody.getStatus()).body(responseBody);
    }

    @PatchMapping("/update/{calId}")
    public ResponseEntity<ResponseBody<CalendarResponse>> updateCalendar(
            @RequestBody CalendarRequest calendarRequest) {

        ResponseBody<CalendarResponse> responseBody = calendarService.updateCalendar(calendarRequest);

        return ResponseEntity.status(responseBody.getStatus()).body(responseBody);
    }

    @GetMapping
    public ResponseEntity<ResponseBody<List<CalendarResponse>>> getCalendar(){

        ResponseBody<List<CalendarResponse>> responseBody = calendarService.getCalendar();

        return ResponseEntity.status(responseBody.getStatus()).body(responseBody);

    }
    //?상의필요
    @GetMapping("/List/{calId}")
    public ResponseEntity<ResponseBody<CalendarResponse>> getDetailCalendar(
            @PathVariable Long calId) {

        ResponseBody<CalendarResponse> responseBody = calendarService.getDetailCalendar(calId);

        return ResponseEntity.status(responseBody.getStatus()).body(responseBody);

    }

    @DeleteMapping("/delete/{calId}")
    public void deleteCalendar(
            @PathVariable Long calId) {

            calendarService.deleteCalendar(calId);
    }
}
