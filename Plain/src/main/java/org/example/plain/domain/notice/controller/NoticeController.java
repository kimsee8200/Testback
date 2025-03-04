package org.example.plain.domain.notice.controller;

import lombok.RequiredArgsConstructor;
import org.example.plain.domain.notice.dto.NoticeRequest;
import org.example.plain.domain.notice.dto.NoticeResponse;
import org.example.plain.domain.notice.service.NoticeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;
    private final NoticeResponse noticeResponse;


    @PostMapping("/{notice}")
    public ResponseEntity<noticeResponse> createNotice(
            @RequestBody NoticeRequest noticeRequest
    ) {
        return ResponseEntity.ok()
                .body(noticeService.createNotice(noticeRequest));
    }

    @PatchMapping("/notice/{notice_id}")
    public ResponseEntity<noticeResponse> updateNotice(
            @PathVariable Long noticeId,
            @RequestBody NoticeRequest noticeRequest
    ) {
        return ResponseEntity.ok()
                .body(noticeService.updateNotice(noticeId, noticeRequest));
    }

    @GetMapping
    public ResponseEntity<List<noticeResponse>> getAllNotice(){
        return ResponseEntity.ok()
                .body(noticeService.getAllNotice());
    }

    @GetMapping("/notice/{notice_id}")
    public ResponseEntity<noticeResponse> getNotice(
            @PathVariable Long noticeId
    ) {
        return ResponseEntity.ok()
                .body(noticeService.getNotice(noticeId));
    }


    @DeleteMapping("/{notice_id}")
    public ResponseEntity<noticeResponse> deleteNotice(
            @PathVariable Long noticeId,
            @RequestHeader(value = "userId") Long userId
    ) {
        NoticeResponse deleteNotice = noticeService.deleteNotice(userId, noticeId);
        return ResponseEntity.ok().body(deleteNotice);
    }

    @PostMapping("/notice/{notice_id}/comments")
    public ResponseEntity<String> createNoticeComments(
            @PathVariable Long noticeId
    ) {
        return ResponseEntity.ok()
                .body(classInviteService.createNoticeComments(noticeId));
    }

    @PutMapping("/notice/{notice_id}/comments/{comment_id}")
    public ResponseEntity<noticeResponse> updateNoticeComments(
            @PathVariable Long noticeId,
            @RequestHeader(value = "userId") Long userId
    ) {
        NoticeResponse deleteNotice = noticeService.updateNoticeComments(userId, noticeId);
        return ResponseEntity.ok().body(deleteNotice);
    }

    @DeleteMapping("/notice/{notice_id}/comments/{comment_id}")
    public ResponseEntity<noticeResponse> deleteNoticeComments(
            @PathVariable Long noticeId,
            @RequestHeader(value = "userId") Long userId
    ) {
        NoticeResponse deleteNotice = noticeService.deleteNoticeComments(userId, noticeId);
        return ResponseEntity.ok().body(deleteNotice);
    }
}
