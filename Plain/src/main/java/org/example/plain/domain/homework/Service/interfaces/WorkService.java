package org.example.plain.domain.homework.Service.interfaces;

import org.example.plain.domain.homework.dto.Work;
import org.example.plain.domain.homework.dto.WorkMember;
import org.example.plain.domain.homework.dto.WorkSubmitField;

import java.util.List;


public interface WorkService {
    void insertWork(Work work);
    void updateWork(Work work, String workId);
    Work selectWork(String WorkId);
    List<Work> selectAllWork();
    void deleteWork(String workId);
    void submitWork(String id, String userId, WorkSubmitField workSubmitField);

    WorkMember getSingleWorkMember(String id, String userId);
    List<WorkMember> getSubmitList(String workId);
    List<WorkMember> getMemberList(String workId);
}
