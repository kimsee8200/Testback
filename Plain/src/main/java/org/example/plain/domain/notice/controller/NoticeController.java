package org.example.plain.domain.notice.controller;

import lombok.RequiredArgsConstructor;
import org.example.plain.domain.notice.dto.NoticeRequest;
import org.example.plain.domain.notice.dto.NoticeResponse;
import org.example.plain.domain.notice.dto.NoticeUpdateRequest;
import org.example.plain.domain.notice.service.NoticeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @PostMapping("/{notice}")
    public ResponseEntity<NoticeResponse> createNotice(
            @RequestBody NoticeRequest noticeRequest
    ) {
        return ResponseEntity.ok()
                .body(noticeService.createNotice(noticeRequest));
    }

    @PatchMapping("/notice/{notice_id}")
    public ResponseEntity<NoticeResponse> updateNotice(
            @RequestBody NoticeUpdateRequest noticeUpdateRequest
    ) {
        return ResponseEntity.ok()
                .body(noticeService.updateNotice(noticeUpdateRequest));
    }

    @GetMapping
    public ResponseEntity<List<NoticeResponse>> getAllNotice(){
        return ResponseEntity.ok()
                .body(noticeService.getAllNotice());
    }

    @GetMapping("/notice/{notice_id}")
    public ResponseEntity<NoticeResponse> getNotice(
            @PathVariable Long noticeId
    ) {
        return ResponseEntity.ok()
                .body(noticeService.getNotice(noticeId));
    }


    @DeleteMapping("/{notice_id}")
    public void deleteNotice(
            @PathVariable Long noticeId
    ) {
        noticeService.deleteNotice(noticeId);
    }
    // 댓글
    @PostMapping("/notice/{notice_id}/comments")
    public ResponseEntity<String> createNoticeComments(
            @PathVariable Long noticeId,
            @RequestBody NoticeRequest noticeRequest
    ) {
        return ResponseEntity.ok()
                .body(noticeService.createNoticeComments(noticeId, noticeRequest));
    }

    @PutMapping("/notice/{notice_id}/comments/{comment_id}")
    public ResponseEntity<NoticeResponse> updateNoticeComments(
            @PathVariable Long noticeId,
            @RequestHeader(value = "userId") Long userId
    ) {
        NoticeResponse deleteNotice = noticeService.updateNoticeComments(userId, noticeId);
        return ResponseEntity.ok().body(deleteNotice);
    }

    @DeleteMapping("/notice/{notice_id}/comments/{comment_id}")
    public ResponseEntity<NoticeResponse> deleteNoticeComments(
            @PathVariable Long noticeId,
            @RequestHeader(value = "userId") Long userId
    ) {
        NoticeResponse deleteNotice = noticeService.deleteNoticeComments(userId, noticeId);
        return ResponseEntity.ok().body(deleteNotice);
    }
}
