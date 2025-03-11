package org.example.plain.domain.notice.controller;

import lombok.RequiredArgsConstructor;
import org.example.plain.common.ResponseBody;
import org.example.plain.domain.notice.dto.*;
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
    public ResponseEntity<ResponseBody<NoticeResponse>> createNotice(
            @RequestBody NoticeRequest noticeRequest) {

        ResponseBody<NoticeResponse> responseBody = noticeService.createNotice(noticeRequest);

        return ResponseEntity.status(responseBody.getStatus()).body(responseBody);
    }

    @PatchMapping("/notice/{notice_id}")
    public ResponseEntity<ResponseBody<NoticeResponse>> updateNotice(
            @RequestBody NoticeUpdateRequest noticeUpdateRequest) {

        ResponseBody<NoticeResponse> responseBody = noticeService.updateNotice(noticeUpdateRequest);

        return ResponseEntity.status(responseBody.getStatus()).body(responseBody);
    }

    @GetMapping
    public ResponseEntity<ResponseBody<List<NoticeResponse>>> getAllNotice(){

        ResponseBody<List<NoticeResponse>> responseBody = noticeService.getAllNotice();

        return ResponseEntity.status(responseBody.getStatus()).body(responseBody);

    }

    @GetMapping("/notice/{notice_id}")
    public ResponseEntity<ResponseBody<NoticeResponse>> getNotice(
            @PathVariable Long noticeId) {

        ResponseBody<NoticeResponse> responseBody = noticeService.getNotice(noticeId);

        return ResponseEntity.status(responseBody.getStatus()).body(responseBody);

    }

    @DeleteMapping("/{notice_id}")
    public void deleteNotice(
            @PathVariable Long noticeId) {

        noticeService.deleteNotice(noticeId);
    }
    // 댓글



    @PostMapping("/notice/{notice_id}/comments")
    public ResponseEntity<ResponseBody<NoticeCommentResponse>> createNoticeComments(
            @RequestBody NoticeCommentRequest noticeCommentRequest) {

        ResponseBody<NoticeCommentResponse> responseBody = noticeService.createNoticeComments(noticeCommentRequest);

        return ResponseEntity.status(responseBody.getStatus()).body(responseBody);

    }

    @PutMapping("/notice/{notice_id}/comments/{comment_id}")
    public ResponseEntity<ResponseBody<NoticeCommentResponse>> updateNoticeComments(
            @RequestBody NoticeCommentUpdateRequest noticeCommentUpdateRequest) {

        ResponseBody<NoticeCommentResponse> responseBody = noticeService.updateNoticeComments(noticeCommentUpdateRequest);
        return ResponseEntity.status(responseBody.getStatus()).body(responseBody);
    }

    @DeleteMapping("/notice/{notice_id}/comments/{comment_id}")
    public void deleteNoticeComments(
            @PathVariable Long commentId) {

        noticeService.deleteNotice(commentId);
    }
}
