package org.example.plain.domain.homework.service;

import lombok.RequiredArgsConstructor;
import org.example.plain.domain.classMember.entity.ClassMember;
import org.example.plain.domain.classMember.entity.ClassMemberId;
import org.example.plain.domain.homework.dto.Work;
import org.example.plain.domain.homework.dto.WorkSubmitField;
import org.example.plain.domain.homework.interfaces.WorkService;
import org.example.plain.domain.homework.dto.WorkSubmitFieldResponse;
import org.example.plain.domain.homework.entity.*;
import org.example.plain.domain.homework.repository.FileRepository;
import org.example.plain.domain.user.dto.CustomUserDetails;
import org.example.plain.domain.user.entity.User;
import org.example.plain.repository.BoardRepository;
import org.example.plain.repository.GroupMemberRepository;
import org.example.plain.repository.WorkMemberRepository;
import org.example.plain.repository.WorkSubmitFieldRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class WorkServiceImpl implements WorkService {
    // 리팩토링 요망.
   // private UserService userService;
    private final SubmitFileService submitFileService;
    private final BoardRepository boardRepository;
    private final WorkSubmitFieldRepository workSubmitFieldRepository;
    private final WorkMemberRepository workMemberRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final FileRepository fileRepository;

    @Value("${file.path}")
    private String filepath;

    public String makeUUID(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    @Transactional
    @Override
    public void insertWork(Work work, String classId, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        ClassMember classMember = groupMemberRepository.findById(new ClassMemberId(classId,userDetails.getUser().getId())).orElseThrow();

        if (!classMember.getClassLecture().getInstructor().getId().equals(userDetails.getUser().getId())) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(403),"접근 권한자가 아닙니다");
        }

        work.setWorkId(makeUUID());
        work.setUserId(userDetails.getUser().getId());
        work.setGroupId(classId);

        WorkEntity workEntity = WorkEntity.workToWorkEntity(work);
       // workEntity.setBoardId(makeUUID()); // 임시 설정.
        workEntity.setUser(classMember.getUser());
        workEntity.setGroup(classMember.getClassLecture());
        boardRepository.save(workEntity);
    }

    @Override
    @Transactional
    public void updateWork(Work work, String workId, String userId) {
        WorkEntity workEntity = boardRepository.findByWorkId(workId).orElseThrow(NullPointerException::new);
        if(!workEntity.getUser().getId().equals(userId)) throw new HttpClientErrorException(HttpStatusCode.valueOf(403),"작성자가 아닙니다.");
        work.setWorkId(workId);
        boardRepository.save(WorkEntity.chaingeWorkEntitytoWork(work,workEntity));
    }

    @Override
    @Transactional
    public Work selectWork(String workId) {
        return Work.changeWorkEntity(boardRepository.findByWorkId(workId).orElseThrow());
    }


    public List<Work> selectGroupWorks(String groupId){
        List<Work> works = new ArrayList<>();
        List<WorkEntity> workEntities = boardRepository.findByGroupId(groupId).orElse(null);
        if (workEntities != null) {
            for (WorkEntity work:workEntities){
                works.add(Work.changeWorkEntity(work));
            }
        }
        return works;
    }


    @Override
    @Transactional
    public void deleteWork(String workId) {
        WorkEntity work = boardRepository.findByWorkId(workId).orElseThrow();
        boardRepository.delete(work);
    }

    @Override
    @Transactional
    public void submitWork(WorkSubmitField workSubmitField) {
        WorkSubmitFieldEntity entity = WorkSubmitFieldEntity.createEntity(workSubmitField);

        List<FileEntity> files;
        List<MultipartFile> multifiles = workSubmitField.getFile();

        Work work = this.selectWork(workSubmitField.getWorkId());

        User user = groupMemberRepository.findById(new ClassMemberId(work.getGroupId(),workSubmitField.getUserId())).orElseThrow().getUser();

        files = submitFileService.changeFile(multifiles,user);

        entity.setFileEntities(files);
        workSubmitFieldRepository.save(entity);

        WorkMemberEntity workMemberEntity = workMemberRepository.findById(new WorkMemberId(workSubmitField.getWorkId(),workSubmitField.getUserId())).orElseThrow();
        if(files.size()>0){
            workMemberEntity.setSubmited(true);
            if(workMemberEntity.getWork().getDeadline().isBefore(LocalDateTime.now()))
                workMemberEntity.setLate(true);
            workMemberRepository.save(workMemberEntity);
        }
    }

    public List<File> getWorkResults(String workId, String userid){
        WorkSubmitFieldId workSubmitFieldId = new WorkSubmitFieldId(userid, workId);
        WorkSubmitFieldEntity workSubmitFieldEntity = workSubmitFieldRepository.findById(workSubmitFieldId).orElseThrow();
        List<File> files = new ArrayList<>();
        for (FileEntity file:workSubmitFieldEntity.getFileEntities()){
            File file1 = new File(filepath+file.getFilename());
            files.add(file1);
        }
        return files;
    }

    @Override
    public List<WorkSubmitFieldResponse> getSubmitList(String workId) {
       List<WorkSubmitFieldResponse> workSubmitFields = new ArrayList<>();
       WorkEntity workEntity = WorkEntity.workToWorkEntity(selectWork(workId));
       for (WorkSubmitFieldEntity workSubmitFieldEntity:workSubmitFieldRepository.findByWorkId(workEntity)){
           WorkSubmitFieldResponse workSubmitField = WorkSubmitFieldResponse.changeEntity(workSubmitFieldEntity);
           workSubmitField.setFile(submitFileService.getFiles(workSubmitFieldEntity.getFileEntities()));
           workSubmitFields.add(workSubmitField);
       }
       return workSubmitFields;
    }

    // file service로 분리 필요. -> 일반 컴포넌트.

}
