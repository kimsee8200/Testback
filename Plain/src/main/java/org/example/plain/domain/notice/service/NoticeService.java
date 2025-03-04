package org.example.plain.domain.notice.service;

import lombok.RequiredArgsConstructor;
import org.example.plain.domain.notice.dto.NoticeRequest;
import org.example.plain.domain.notice.dto.NoticeResponse;
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
        User user =  userRepository.getReferenceById(noticeRequest.getUserId()).orElseThrow();

        NoticeEntity createNotice = noticeRequest.toEntity(user);
        noticeRepository.save(createNotice);

        return NoticeResponse.builder()
                .noticeId(createNotice.getNoticeId())
                .title(noticeRequest.title())
                .content(noticeRequest.content())
                .userId(noticeRequest.userId())
                .build();
    }


    /**
     * 공지사항 수정
     * @param userId
     * @param classId
     * @param classRequest
     * @return
     */
    public NoticeResponse updateNotice(String noticeId, NoticeRequest noticeRequest) {
        NoticeEntity noticeEntity = .findById(noticeId);
        User user = noticeRepository.findById(userId).orElseThrow();

        if (noticeEntity.getUserId().equals(user.getUserId())) {
            throw new RuntimeException("수정 할 수 없습니다");
        }
        String title = classRequest.title();
        String description = classRequest.description();

        noticeEntity.updateClass(title, description);
        classLectureRepositoryPort.save(noticeEntity);

        return NoticeResponse.builder()
                .noticeId(noticeEntity.getNoticeId())
                .title(noticeEntity.getTitle())
                .content(noticeEntity.getContent())
                .userId(noticeEntity.getUserId())
                .build();
    }

    public List<ClassResponse> getAllNotice(){
        List<ClassLecture> classes = classLectureRepositoryPort.findAll();
        return classes.stream()
                .map(c -> ClassResponse.builder()
                        .id(c.getId())
                        .title(c.getTitle())
                        .description(c.getDescription())
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
