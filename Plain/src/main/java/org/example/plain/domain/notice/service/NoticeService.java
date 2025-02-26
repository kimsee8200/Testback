package org.example.plain.domain.notice.service;

import lombok.RequiredArgsConstructor;
import org.example.plain.domain.notice.dto.NoticeRequest;
import org.example.plain.domain.notice.dto.NoticeResponse;
import org.example.plain.domain.notice.entity.NoticeEntity;
import org.example.plain.domain.notice.repository.NoticeRepository;
import org.example.plain.domain.user.entity.User;

import java.util.List;

@service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public NoticeResponse createNotice(NoticeRequest noticeRequest) {

        String title = noticeRequest.title();
        String content = noticeRequest.content();
        String userId = noticeRequest.userId();

        NoticeEntity createNotice = noticeRequest.toEntity(user);
        noticeRepository.save(createNotice);

        return NoticeResponse.builder()
                .noticeId(noticeRepository.getId())
                .title(title)
                .content(content)
                .userId(userId)
                .build();
    }

    public ClassResponse getClass(Long classId){
        ClassLecture classLecture = classLectureRepositoryPort.findById(classId);

        return ClassResponse.builder()
                .id(classLecture.getId())
                .title(classLecture.getTitle())
                .description(classLecture.getDescription())
                .code(classLecture.getCode())
                .build();
    }

    public List<ClassResponse> getAllClass(){
        List<ClassLecture> classes = classLectureRepositoryPort.findAll();
        return classes.stream()
                .map(c -> ClassResponse.builder()
                        .id(c.getId())
                        .title(c.getTitle())
                        .description(c.getDescription())
                        .build())
                .toList();
    }

    /**
     * 클래스 삭제
     * @param userId
     * @param classId
     */
    public ClassResponse deleteClass(Long userId, Long classId){
        ClassLecture classLecture = classLectureRepositoryPort.findById(classId);
        User user = userRepository.findById(userId).orElseThrow();

        if (classLecture.getInstructor().getUserId().equals(user.getUserId())) {
            throw new RuntimeException("삭제 할 수 없습니다");
        }

        classLectureRepositoryPort.delete(classId);
        return ClassResponse.builder()
                .id(classLecture.getId())
                .title(classLecture.getTitle())
                .description(classLecture.getDescription())
                .code(classLecture.getCode())
                .build();
    }

    /**
     * 클래스 수정
     * @param userId
     * @param classId
     * @param classRequest
     * @return
     */
    public ClassResponse modifiedClass(Long userId, Long classId, ClassRequest classRequest) {
        ClassLecture classLecture = classLectureRepositoryPort.findById(classId);
        User user = userRepository.findById(userId).orElseThrow();

        if (classLecture.getInstructor().getUserId().equals(user.getUserId())) {
            throw new RuntimeException("수정 할 수 없습니다");
        }
        String title = classRequest.title();
        String description = classRequest.description();

        classLecture.updateClass(title, description);
        classLectureRepositoryPort.save(classLecture);

        return ClassResponse.builder()
                .id(classLecture.getId())
                .title(classLecture.getTitle())
                .description(classLecture.getDescription())
                .build();
    }

}
