package org.example.plain.domain.homework.interfaces;

import org.example.plain.domain.homework.dto.Work;
import org.example.plain.domain.homework.dto.WorkSubmitField;
import org.example.plain.domain.homework.dto.WorkSubmitFieldResponse;
import org.springframework.security.core.Authentication;

import java.util.List;


public interface WorkService {
    void insertWork(Work work, String groupId, Authentication authentication);

    void updateWork(Work work, String workId, String userId);

    Work selectWork(String WorkId);

    void deleteWork(String workId);

    void submitWork(WorkSubmitField workSubmitField);

    List<Work> selectGroupWorks(String groupId);

    List<WorkSubmitFieldResponse> getSubmitList(String workId);
}
