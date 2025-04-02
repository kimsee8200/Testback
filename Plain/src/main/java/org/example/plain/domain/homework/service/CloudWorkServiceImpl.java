package org.example.plain.domain.homework.service;

import lombok.RequiredArgsConstructor;
import org.example.plain.domain.classMember.entity.ClassMember;
import org.example.plain.domain.classMember.entity.ClassMemberId;
import org.example.plain.domain.file.dto.SubmitFileData;
import org.example.plain.domain.file.entity.FileEntity;
import org.example.plain.domain.file.interfaces.CloudFileService;
import org.example.plain.domain.homework.dto.*;
import org.example.plain.domain.homework.interfaces.SubmissionService;
import org.example.plain.domain.homework.interfaces.WorkService;
import org.example.plain.domain.homework.entity.*;
import org.example.plain.domain.homework.repository.FileRepository;
import org.example.plain.domain.user.dto.CustomUserDetails;
import org.example.plain.domain.user.entity.User;
import org.example.plain.repository.BoardRepository;
import org.example.plain.repository.GroupMemberRepository;
import org.example.plain.repository.WorkMemberRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.*;

import static org.example.plain.common.service.UUIDService.makeUUID;

@Service
@RequiredArgsConstructor
public class CloudWorkServiceImpl implements WorkService {
    // 리팩토링 요망.
   // private UserService userService;
    private final CloudFileService fileService;
    private final BoardRepository boardRepository;
    private final WorkMemberRepository workMemberRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final FileRepository fileRepository;
    private final SubmissionService submissionService;

    @Value("${file.path}")
    private String filepath;



    @Transactional
    @Override
    public void insertWork(Work work, String classId, String userId) {
        ClassMember classMember = groupMemberRepository.findById(new ClassMemberId(classId, userId))
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "수업 멤버를 찾을 수 없습니다."));

        if (!classMember.getClassLecture().getInstructor().getId().equals(userId)) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
        }

        work.setWorkId(makeUUID());
        work.setUserId(userId);
        work.setGroupId(classId);

        WorkEntity workEntity = WorkEntity.workToWorkEntity(work);
        workEntity.setUser(classMember.getUser());
        workEntity.setGroup(classMember.getClassLecture());
        boardRepository.save(workEntity);
    }

    @Override
    @Transactional
    public void updateWork(Work work, String workId, String userId) {
        WorkEntity workEntity = boardRepository.findByWorkId(workId)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "과제를 찾을 수 없습니다."));
        
        if (!workEntity.getUser().getId().equals(userId)) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "작성자가 아닙니다.");
        }
        
        work.setWorkId(workId);
        boardRepository.save(WorkEntity.chaingeWorkEntitytoWork(work, workEntity));
    }

    @Override
    @Transactional(readOnly = true)
    public Work selectWork(String workId) {
        return Work.changeWorkEntity(boardRepository.findByWorkId(workId)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "과제를 찾을 수 없습니다.")));
    }

    @Transactional(readOnly = true)
    public List<Work> selectGroupWorks(String groupId) {
        return boardRepository.findByGroupId(groupId)
                .map(workEntities -> workEntities.stream()
                        .map(Work::changeWorkEntity)
                        .toList())
                .orElse(new ArrayList<>());
    }

    @Override
    @Transactional
    public void deleteWork(String workId) {
        WorkEntity work = boardRepository.findByWorkId(workId)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "과제를 찾을 수 없습니다."));
        boardRepository.delete(work);
    }

  

    public List<File> getWorkResults(String workId, String userId){
        WorkMemberEntity workMemberEntity = workMemberRepository.findById(new WorkMemberId(workId,userId)).orElseThrow();

        List<File> files = new ArrayList<>();
        for (FileEntity file:workMemberEntity.getFileEntities()){
            File file1 = new File(filepath+file.getFilename());
            files.add(file1);
        }
        return files;
    }

    // file service로 분리 필요. -> 일반 컴포넌트.

}
