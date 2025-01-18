package org.example.plain.domain.homework.Service.serviceImpl;

import org.example.plain.domain.board.dto.Board;
import org.example.plain.domain.homework.dao.WorkDao;
import org.example.plain.domain.board.BoardService;
import org.example.plain.domain.homework.dao.WorkMemberDao;
import org.example.plain.domain.homework.dto.Work;
import org.example.plain.domain.homework.dto.WorkMember;
import org.example.plain.domain.homework.dto.WorkSubmitField;
import org.example.plain.domain.homework.Service.interfaces.WorkService;
import org.example.plain.domain.homework.entity.WorkEntity;
import org.example.plain.domain.homework.entity.WorkMemberEntity;
import org.example.plain.domain.user.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkServiceImpl implements WorkService {
    private WorkDao workDao;
    private WorkMemberDao workMemberDao;
   // private UserService userService;
    private BoardService boardService;


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
        List<Work> workList = new ArrayList<>();
        for(WorkEntity work : workDao.findAll()){
            if(work.getGroupId().equals(groupId)){
                workList.add(Work.changeWorkEntity(work));
            }
        }
        return workList;
    }

    public List<Work> selectGroupAndUserWork(String groupId, String userId){
        List<Work> workList = new ArrayList<>();
        for(WorkEntity work : workDao.findAll()){

        }
    }

    @Override
    public void deleteWork(String workId) {
        WorkEntity work = workDao.findById(workId);
        workDao.delete(work);
    }

    @Override
    public void submitWork(String id, String UserId, WorkSubmitField workSubmitField) {
    }

    @Override
    public List<WorkMember> getSubmitList(String workId) {
        return List.of();
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
}
