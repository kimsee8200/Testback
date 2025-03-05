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

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;

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
     * @param userId
     * @param classId
     * @param classRequest
     * @return
     */
    public NoticeResponse updateNotice(NoticeUpdateRequest noticeUpdateRequest) {

        NoticeEntity noticeEntity = NoticeRepository.findById(noticeUpdateRequest.getNoticeId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));


        NoticeEntity updateNotice = NoticeEntity.update(
                noticeUpdateRequest.getNoticeId(),
                noticeUpdateRequest.getTitle(),
                noticeUpdateRequest.getContent(),
                user

        );

        return NoticeResponse.from(updateNotice);
    }

    public List<NoticeResponse> getAllNotice(){
        List<NoticeEntity> allNotice = NoticeRepository.findAll();
        return allNotice.stream()
                .map(c -> NoticeResponse.builder()
                        .userId(c.getUser().getId())
                        .title(c.getTitle())
                        .content(c.getContent())
                        .createDate(c.getCreateDate())
                        .modifiedAt(c.getModifiedAt())
                        .build())
                .toList();
    }

    public NoticeResponse getNotice(Long classId){
        ClassLecture classLecture = classLectureRepositoryPort.findById(classId);

        return ClassResponse.builder()
                .id(classLecture.getId())
                .title(classLecture.getTitle())
                .description(classLecture.getDescription())
                .code(classLecture.getCode())
                .build();
    }



    /**
     * 클래스 삭제
     * @param userId
     * @param classId
     */
    public NoticeResponse deleteNotice(Long userId, Long classId){
        ClassLecture classLecture = classLectureRepositoryPort.findById(classId);
        User user = userRepository.findById(userId).orElseThrow();

        if (classLecture.getInstructor().getUserId().equals(user.getUserId())) {
            throw new RuntimeException("삭제 할 수 없습니다");
        }

        classLectureRepositoryPort.delete(classId);
        return NoticeResponse.builder()
                .id(classLecture.getId())
                .title(classLecture.getTitle())
                .description(classLecture.getDescription())
                .code(classLecture.getCode())
                .build();
    }


}
