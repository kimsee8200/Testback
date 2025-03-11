package org.example.plain.domain.notice.service;

import lombok.RequiredArgsConstructor;
import org.example.plain.common.ResponseBody;
import org.example.plain.common.enums.Message;
import org.example.plain.domain.notice.dto.*;
import org.example.plain.domain.notice.entity.NoticeCommentEntity;
import org.example.plain.domain.notice.entity.NoticeEntity;
import org.example.plain.domain.notice.repository.NoticeCommentRepository;
import org.example.plain.domain.notice.repository.NoticeRepository;
import org.example.plain.domain.user.entity.User;
import org.example.plain.domain.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final NoticeCommentRepository noticeCommentRepository;
    private final UserRepository userRepository;

    @Transactional
    public ResponseBody<NoticeResponse> createNotice(NoticeRequest noticeRequest) {
        // User ID로 User 조회
        User user = userRepository.findById(noticeRequest.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        // NoticeEntity 생성 (User 포함)
        NoticeEntity createNotice = NoticeEntity.create(
                noticeRequest.getTitle(),
                noticeRequest.getContent(),
                user
        );

        // 저장
        NoticeEntity noticeEntity = noticeRepository.save(createNotice);

        // NoticeResponse 생성 및 반환
        return new ResponseBody<>(Message.OK.name(), HttpStatus.OK, NoticeResponse.from(noticeEntity));
    }


    /**
     * 공지사항 수정
     * @param noticeUpdateRequest
     * @return
     */
    @Transactional
    public ResponseBody<NoticeResponse> updateNotice(NoticeUpdateRequest noticeUpdateRequest) {

        NoticeEntity noticeEntity = noticeRepository.findById(noticeUpdateRequest.getNoticeId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

                noticeEntity.update(
                noticeUpdateRequest.getNoticeId(),
                noticeUpdateRequest.getTitle(),
                noticeUpdateRequest.getContent()

        );

        return new ResponseBody<>(Message.OK.name(), HttpStatus.OK, NoticeResponse.from(noticeEntity));
    }

    public ResponseBody<List<NoticeResponse>> getAllNotice(){
        List<NoticeEntity> allNotice = noticeRepository.findAll();

        List<NoticeResponse> noticeResponses = allNotice.stream()
                .map(c -> NoticeResponse.builder()
                        .user(c.getUser())
                        .title(c.getTitle())
                        .content(c.getContent())
                        .createDate(c.getCreateDate())
                        .modifiedAt(c.getModifiedAt())
                        .build())
                .toList();

        return new ResponseBody<>(Message.OK.name(), HttpStatus.OK, noticeResponses);
    }

    public ResponseBody<NoticeResponse> getNotice(Long noticeId){
       return new ResponseBody<>(Message.OK.name(), HttpStatus.OK, NoticeResponse.from(noticeRepository.findById(noticeId).orElseThrow()));
    }

    /**
     * 클래스 삭제
     * @param noticeId
     */
    @Transactional
    public void deleteNotice(Long noticeId) {
        NoticeEntity noticeEntity = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        noticeRepository.delete(noticeEntity);
    }

    @Transactional
    public ResponseBody<NoticeCommentResponse> createNoticeComments(NoticeCommentRequest noticeCommentRequest) {
        // User ID로 User 조회
        User user = userRepository.findById(noticeCommentRequest.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        // NoticeEntity 생성 (User 포함)
        NoticeCommentEntity createNoticeComments = NoticeCommentEntity.create(
                noticeCommentRequest.getTitle(),
                noticeCommentRequest.getContent(),
                noticeCommentRequest.getNoticeId(),
                user
        );

        // 저장
        NoticeCommentEntity noticeCommentEntity = noticeCommentRepository.save(createNoticeComments);

        // NoticeResponse 생성 및 반환
        return new ResponseBody<>(Message.OK.name(), HttpStatus.OK, NoticeCommentResponse.from(noticeCommentEntity));
    }

    @Transactional
    public ResponseBody<NoticeCommentResponse> updateNoticeComments(NoticeCommentUpdateRequest noticeCommentUpdateRequest) {

        NoticeCommentEntity noticeCommentEntity = noticeCommentRepository.findById(noticeCommentUpdateRequest.getCommentId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        noticeCommentEntity.update(
                noticeCommentUpdateRequest.getCommentId(),
                noticeCommentUpdateRequest.getNoticeId(),
                noticeCommentUpdateRequest.getTitle(),
                noticeCommentUpdateRequest.getContent()


        );

        return new ResponseBody<>(Message.OK.name(), HttpStatus.OK, NoticeCommentResponse.from(noticeCommentEntity));
    }

    /**
     * 클래스 삭제
     * @param commentId
     */
    @Transactional
    public void deleteCommentNotice(Long commentId) {
        NoticeCommentEntity noticeCommentEntity = noticeCommentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        noticeCommentRepository.delete(noticeCommentEntity);
    }

}
