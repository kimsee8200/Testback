package org.example.plain.domain.notice.service;

import lombok.RequiredArgsConstructor;
import org.example.plain.domain.notice.dto.NoticeRequest;
import org.example.plain.domain.notice.dto.NoticeResponse;
import org.example.plain.domain.notice.dto.NoticeUpdateRequest;
import org.example.plain.domain.notice.entity.NoticeEntity;
import org.example.plain.domain.notice.repository.NoticeRepository;
import org.example.plain.domain.user.entity.User;
import org.example.plain.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;

    @Transactional
    public NoticeResponse createNotice(NoticeRequest noticeRequest) {
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
        return NoticeResponse.from(noticeEntity);
    }


    /**
     * 공지사항 수정
     * @param noticeUpdateRequest
     * @return
     */
    @Transactional
    public NoticeResponse updateNotice(NoticeUpdateRequest noticeUpdateRequest) {

        NoticeEntity noticeEntity = noticeRepository.findById(noticeUpdateRequest.getNoticeId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

                noticeEntity.update(
                noticeUpdateRequest.getNoticeId(),
                noticeUpdateRequest.getTitle(),
                noticeUpdateRequest.getContent()

        );

        return NoticeResponse.from(noticeEntity);
    }

    public List<NoticeResponse> getAllNotice(){
        List<NoticeEntity> allNotice = noticeRepository.findAll();
        return allNotice.stream()
                .map(c -> NoticeResponse.builder()
                        .user(c.getUser())
                        .title(c.getTitle())
                        .content(c.getContent())
                        .createDate(c.getCreateDate())
                        .modifiedAt(c.getModifiedAt())
                        .build())
                .toList();
    }

    public NoticeResponse getNotice(Long noticeId){
       return NoticeResponse.from(noticeRepository.findById(noticeId).orElseThrow());
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
    public NoticeResponse createNotice(NoticeRequest noticeRequest) {
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
        return NoticeResponse.from(noticeEntity);
    }

}
