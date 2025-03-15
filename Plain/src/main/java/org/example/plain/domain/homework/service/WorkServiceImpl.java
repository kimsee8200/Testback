package org.example.plain.domain.homework.service;

import lombok.RequiredArgsConstructor;
import org.example.plain.domain.groupmember.entity.GroupMember;
import org.example.plain.domain.groupmember.entity.GroupMemberId;
import org.example.plain.domain.homework.dto.Work;
import org.example.plain.domain.homework.dto.WorkSubmitField;
import org.example.plain.domain.homework.interfaces.WorkService;
import org.example.plain.domain.homework.dto.WorkSubmitFieldResponse;
import org.example.plain.domain.homework.entity.*;
import org.example.plain.domain.user.dto.CustomUserDetails;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkServiceImpl implements WorkService {
    // 리팩토링 요망.
   // private UserService userService;
    private final BoardRepository boardRepository;
    private final WorkSubmitFieldRepository workSubmitFieldRepository;
    private final WorkMemberRepository workMemberRepository;
    private final GroupMemberRepository groupMemberRepository;

    @Value("${file.path}")
    private String filepath;

    public String makeUUID(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    @Transactional
    @Override
    public void insertWork(Work work, String groupId, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        GroupMember groupMember = groupMemberRepository.findById(new GroupMemberId(groupId,userDetails.getUser().getId())).orElseThrow();

        if (!groupMember.getGroup().getInstructor().getId().equals(userDetails.getUser().getId())) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(403),"접근 권한자가 아닙니다");
        }

        WorkEntity workEntity = WorkEntity.workToWorkEntity(work);
       // workEntity.setBoardId(makeUUID()); // 임시 설정.
        workEntity.setWorkId(makeUUID());
        workEntity.setUserId(userDetails.getUser().getId());
        workEntity.setGroupId(groupId);
        workEntity.setUser(groupMember.getUser());
        workEntity.setGroup(groupMember.getGroup());
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

        List<FileEntity> files = new ArrayList<>();
        List<MultipartFile> multifiles = workSubmitField.getFile();
        for(MultipartFile file : multifiles){

            FileEntity fileEntity = new FileEntity();
            String filename = makeFilename(file.getOriginalFilename(),workSubmitField.getUserId());

            saveFile(file,filename);

            fileEntity.setFilename(filename);
            files.add(fileEntity);
        }
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
            File file1 = new File(file.getFilename());
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
           workSubmitField.setFile(getFiles(workSubmitFieldEntity.getFileEntities()));
           workSubmitFields.add(workSubmitField);
       }
       return workSubmitFields;
    }

    public File getFile (String filename){
        File file = new File(filepath+filename).exists() ? new File(filepath+filename):null;
        return file;
    }



    private String makeFilename (String originalFilename, String userId){
        Integer count = 1;
        int split = originalFilename.lastIndexOf(".");
        String name = originalFilename.substring(0, split);
        String extending = originalFilename.substring(split);
        String addSide = "";
        while(new File(userId+name+addSide+extending).exists()){
            addSide = "(" + count + ")";
            count++;
        }
        return userId+name+addSide+extending;
    }

    private File saveFile(MultipartFile file, String filename) {
        File file1 = new File(filepath+filename);
        try {
            file.transferTo(file1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file1;
    }

    private List<File> getFiles(List<FileEntity> fileEntities) {
        List<File> files = new ArrayList<>();
        for (FileEntity fileEntity:fileEntities){
            File file1 = new File(fileEntity.getFilename());
            files.add(file1);
        }
        return files;
    }
}
