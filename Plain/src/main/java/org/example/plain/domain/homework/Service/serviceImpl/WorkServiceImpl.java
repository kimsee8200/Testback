package org.example.plain.domain.homework.Service.serviceImpl;

import org.example.plain.domain.homework.dao.WorkDao;
import org.example.plain.domain.board.BoardService;
import org.example.plain.domain.homework.dto.Work;
import org.example.plain.domain.homework.dto.WorkMember;
import org.example.plain.domain.homework.dto.WorkSubmitField;
import org.example.plain.domain.homework.Service.interfaces.WorkService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkServiceImpl implements WorkService {
    private WorkDao workDao;
    private BoardService boardService;


    @Override
    public void insertWork(Work work) {

    }

    @Override
    public void updateWork(Work work, String workId) {

    }

    @Override
    public Work selectWork(String WorkId) {
        return null;
    }

    @Override
    public List<Work> selectAllWork() {
        return List.of();
    }

    @Override
    public void deleteWork(String workId) {

    }

    @Override
    public void submitWork(String id, String id1, WorkSubmitField workSubmitField) {

    }

    @Override
    public WorkMember getSingleWorkMember(String id, String userId) {
        return null;
    }

    @Override
    public List<WorkMember> getSubmitList(String workId) {
        return List.of();
    }

    @Override
    public List<WorkMember> getMemberList(String workId) {
        return List.of();
    }
}
