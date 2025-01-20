//package org.example.plain.testMock;
//
//
//import org.example.plain.domain.homework.dao.WorkDao;
//import org.example.plain.domain.homework.dto.WorkMember;
//import org.example.plain.domain.homework.dto.WorkSubmitField;
//import org.example.plain.domain.homework.Service.interfaces.WorkService;
//import org.example.plain.domain.homework.dto.Work;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class MockWorkServiceImpl implements WorkService {
//
//    private WorkDao workDao;
//
//    public MockWorkServiceImpl(WorkDao workDao) {
//        this.workDao = workDao;
//    }
//
//
//    @Override
//    public void insertWork(Work work) {
//        workDao.insertHomework(work);
//    }
//
//    @Override
//    public void updateWork(Work work, String id) {
//        workDao.updateHomework(work, id);
//    }
//
//    @Override
//    public Work selectWork(String id) {
//        return workDao.selectHomework(id);
//    }
//
//    @Override
//    public List<Work> selectAllWork() {
//        return workDao.selectAllHomework();
//    }
//
//    @Override
//    public void deleteWork(String id) {
//        workDao.deleteHomework(workDao.selectHomework(id));
//    }
//
//    public void deleteAll(){
//        workDao.deleteAll();
//    }
//
//    @Override
//    public void submitWork(String id, String id1, WorkSubmitField workSubmitField) {
//        //1. 유저가 그룹에 소속되어있는지 검증
//        //2. work가 존재하는지 검증, 만료일 확인 -> 만료되었다면 표시함.
//        //3. WorkSubmitField를 저장한다.
//        //4. 자신의 workMember 필드중 isSubmit을 true로 변경.
//    }
//
//    @Override
//    public WorkMember getSingleWorkMember(String id, String userId) {
//        return null;
//    }
//
//    @Override
//    public List<WorkMember> getSubmitList(String workId) {
//        return List.of();
//    }
//
//    @Override
//    public List<WorkMember> getMemberList(String workId) {
//        return List.of();
//    }
//}
