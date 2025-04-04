package org.example.plain.domain.homework.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.plain.domain.classMember.entity.ClassMember;
import org.example.plain.domain.classMember.entity.ClassMemberId;
import org.example.plain.domain.file.dto.SubmitFileData;
import org.example.plain.domain.file.entity.FileEntity;
import org.example.plain.domain.file.interfaces.CloudFileService;
import org.example.plain.domain.homework.dto.WorkSubmitField;
import org.example.plain.domain.homework.dto.WorkSubmitListResponse;
import org.example.plain.domain.homework.entity.WorkEntity;
import org.example.plain.domain.homework.entity.WorkMemberEntity;
import org.example.plain.domain.homework.entity.WorkMemberId;
import org.example.plain.domain.homework.interfaces.SubmissionService;
import org.example.plain.domain.homework.interfaces.WorkService;
import org.example.plain.domain.user.entity.User;
import org.example.plain.repository.GroupMemberRepository;
import org.example.plain.repository.WorkMemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubmissionServiceImpl implements SubmissionService {

    private final CloudFileService fileService;
    private final WorkMemberRepository workMemberRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final WorkService workService;

    @Override
    @Transactional
    public void submit(WorkSubmitField workSubmitField) {
        WorkMemberEntity workMemberEntity = validateWorkMember(workSubmitField.getWorkId(), workSubmitField.getUserId());
        WorkEntity work = WorkEntity.workToWorkEntity(workService.selectWork(workSubmitField.getWorkId()));
        
        // 마감일 체크
        validateDeadline(work);
        
        // 클래스 멤버 검증 (submit에서는 다른 에러 메시지 사용)
        User user = groupMemberRepository.findById(new ClassMemberId(work.getClassId(), workSubmitField.getUserId()))
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "수업 멤버를 찾을 수 없습니다"))
                .getUser();

        try {
            List<FileEntity> files = fileService.uploadFiles(
                    SubmitFileData.builder()
                            .userId(user)
                            .workId(work)
                            .build(),
                    workSubmitField.getFile()
            );

            workMemberEntity.setFileEntities(files);
            workMemberEntity.setSubmited(true);
            workMemberEntity.setLate(work.getDeadline().isBefore(LocalDateTime.now()));
            workMemberRepository.save(workMemberEntity);
            
            log.info("과제 제출 성공 - 과제ID: {}, 사용자ID: {}", workSubmitField.getWorkId(), workSubmitField.getUserId());
        } catch (Exception e) {
            log.error("과제 제출 실패 - 과제ID: {}, 사용자ID: {}", workSubmitField.getWorkId(), workSubmitField.getUserId(), e);
            throw new RuntimeException("과제 제출에 실패했습니다.", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getSubmissionFiles(String workId, String userId) {
        WorkMemberEntity workMemberEntity = validateWorkMember(workId, userId);
        
        // Check if the user is a class member
        WorkEntity work = workMemberEntity.getWork();
        validateClassMember(work.getClassId(), userId);

        return workMemberEntity.getFileEntities().stream()
                .map(FileEntity::getFilePath)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkSubmitListResponse> getSubmissionList(String workId) {
        WorkEntity workEntity = WorkEntity.workToWorkEntity(workService.selectWork(workId));
        return workMemberRepository.findByWork(workEntity).stream()
                .map(WorkSubmitListResponse::changeEntity)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isSubmitted(String workId, String userId) {
        return workMemberRepository.findById(new WorkMemberId(workId, userId))
                .map(workMemberEntity -> {
                    // 클래스 멤버 검증
                    WorkEntity work = workMemberEntity.getWork();
                    validateClassMember(work.getClassId(), userId);
                    return workMemberEntity.isSubmited();
                })
                .orElse(false);
    }

    @Override
    @Transactional
    public void cancelSubmission(String workId, String userId) {
        WorkMemberEntity workMemberEntity = validateWorkMember(workId, userId);
        
        // 클래스 멤버 검증
        WorkEntity work = workMemberEntity.getWork();
        validateClassMember(work.getClassId(), userId);

        try {
            // S3에서 파일 삭제
            workMemberEntity.getFileEntities().forEach(fileEntity -> {
                try {
                    fileService.deleteFile(fileEntity.getFilePath());
                } catch (Exception e) {
                    log.error("S3 파일 삭제 실패 - 파일URL: {}", fileEntity.getFilePath(), e);
                }
            });

            // DB에서 파일 정보 삭제
            workMemberEntity.setFileEntities(List.of());
            workMemberEntity.setSubmited(false);
            workMemberEntity.setLate(false);
            workMemberRepository.save(workMemberEntity);
            
            log.info("과제 제출 취소 성공 - 과제ID: {}, 사용자ID: {}", workId, userId);
        } catch (Exception e) {
            log.error("과제 제출 취소 실패 - 과제ID: {}, 사용자ID: {}", workId, userId, e);
            throw new RuntimeException("과제 제출 취소에 실패했습니다.", e);
        }
    }
    
    /**
     * 과제 제출 정보 검증
     * @param workId 과제 ID
     * @param userId 사용자 ID
     * @return 검증된 WorkMemberEntity
     */
    private WorkMemberEntity validateWorkMember(String workId, String userId) {
        return workMemberRepository.findById(new WorkMemberId(workId, userId))
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "과제 제출 정보를 찾을 수 없습니다."));
    }
    
    /**
     * 클래스 멤버 검증
     * @param classId 클래스 ID
     * @param userId 사용자 ID
     * @return 검증된 User
     */
    private User validateClassMember(String classId, String userId) {
        return groupMemberRepository.findById(new ClassMemberId(classId, userId))
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.FORBIDDEN, "클래스 멤버가 아닙니다"))
                .getUser();
    }
    
    /**
     * 과제 마감일 검증
     * @param work 과제 엔티티
     */
    private void validateDeadline(WorkEntity work) {
        LocalDateTime now = LocalDateTime.now();
        if (work.getDeadline().isBefore(now)) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "과제 제출 기한이 지났습니다.");
        }
    }
}