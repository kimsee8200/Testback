package org.example.plain.domain.calendar.controller;

import lombok.RequiredArgsConstructor;
import org.example.plain.common.ResponseField;
import org.example.plain.common.enums.Category;
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
    public ResponseEntity<ResponseField<CalendarResponse>> insertCalendar(
            @RequestBody CalendarRequest calendarRequest) {

        ResponseField<CalendarResponse> responseBody = calendarService.insertCalendar(calendarRequest);

        return ResponseEntity.status(responseBody.getStatus()).body(responseBody);
    }

    @PatchMapping("/update/{calId}")
    public ResponseEntity<ResponseField<CalendarResponse>> updateCalendar(
            @RequestBody CalendarRequest calendarRequest) {

        ResponseField<CalendarResponse> responseBody = calendarService.updateCalendar(calendarRequest);

        return ResponseEntity.status(responseBody.getStatus()).body(responseBody);
    }

    @GetMapping("/List/{category}")
    public ResponseEntity<ResponseField<List<CalendarResponse>>> getCalendar(@PathVariable Category category){

        ResponseField<List<CalendarResponse>> responseBody = calendarService.getCalendar(category);

        return ResponseEntity.status(responseBody.getStatus()).body(responseBody);

    }
    //?상의필요
    @GetMapping("/List/{calId}")
    public ResponseEntity<ResponseField<CalendarResponse>> getDetailCalendar(
            @PathVariable Long calId) {

        ResponseField<CalendarResponse> responseBody = calendarService.getDetailCalendar(calId);

        return ResponseEntity.status(responseBody.getStatus()).body(responseBody);

    }

    @DeleteMapping("/delete/{calId}")
    public void deleteCalendar(
            @PathVariable Long calId) {

            calendarService.deleteCalendar(calId);
    }
}
