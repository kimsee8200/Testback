package org.example.plain.domain.homework.Service.serviceImpl;

import org.example.plain.domain.homework.dao.WorkDao;
import org.example.plain.domain.board.BoardService;
import org.example.plain.domain.homework.dao.WorkMemberDao;
import org.example.plain.domain.homework.dto.Work;
import org.example.plain.domain.homework.dto.WorkMember;
import org.example.plain.domain.homework.dto.WorkSubmitField;
import org.example.plain.domain.homework.Service.interfaces.WorkService;
import org.example.plain.domain.homework.dto.WorkSubmitFieldResponse;
import org.example.plain.domain.homework.entity.*;
import org.example.plain.repository.WorkMemberRepository;
import org.example.plain.repository.WorkSubmitFieldRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class WorkServiceImpl implements WorkService {
    private WorkDao workDao;
    private WorkMemberDao workMemberDao;
   // private UserService userService;
    private BoardService boardService;
    private WorkSubmitFieldRepository workSubmitFieldRepository;
    private WorkMemberRepository workMemberRepository;

    @Value("${file.path}")
    private String filepath;

    @Override
    public void insertWork(Work work) {
        if(!work.getBoardId().isEmpty()) return;
        WorkEntity workEntity = WorkEntity.workToWorkEntity(work);
        //UserEntity userEntity = userService.findByUserId(userId);
        //workEntity.setUser(user);
        workDao.save(workEntity);
    }

    @Override
    public void updateWork(Work work, String workId) {
        // work 존재 확인.
        // 선생님인지 확인 -> spring secuirty
        // or 선생님 한명만 수정할 수 있게 userId로 판단.
        // work null 값 판단.
        work.setWorkId(workId);
        workDao.update(WorkEntity.workToWorkEntity(work));
    }

    @Override
    public Work selectWork(String WorkId) {
        return Work.changeWorkEntity(workDao.findById(WorkId));
    }

    @Override
    public List<Work> selectAllWork() {
        return List.of();
    }

    public List<Work> selectGroupWork(String groupId){
        return selectGroupWork(groupId);
    }

//    public List<Work> selectGroupAndUserWork(String groupId, String userId){
//        List<Work> workList = new ArrayList<>();
//        for(WorkEntity work : workDao.findAll()){
//
//        }
//    }

    @Override
    public void deleteWork(String workId) {
        WorkEntity work = workDao.findById(workId);
        workDao.delete(work);
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
       for (WorkSubmitFieldEntity workSubmitFieldEntity:workSubmitFieldRepository.findByWorkId(workId)){
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

    @Override
    public List<WorkMember> getMemberList(String workId) {
        List<WorkMember> workMemberList = new ArrayList<>();
        for (WorkMemberEntity workMemberEntity:workMemberDao.findByWorkId(workId)){
            workMemberList.add(WorkMember.changeEntity(workMemberEntity));
        }
        return workMemberList;
    }

    public void addWorkMember(String workId, String memberId) throws Exception {
        WorkMemberEntity workMemberEntity = WorkMemberEntity.makeWorkMemberEntity(memberId, workId);
        workMemberDao.save(workMemberEntity);
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
