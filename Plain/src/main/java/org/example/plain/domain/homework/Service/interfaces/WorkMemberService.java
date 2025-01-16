package org.example.plain.domain.homework.Service.interfaces;

import org.example.plain.domain.homework.dto.WorkMember;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WorkMemberService {
    public void addHomeworkMember(String homeworkId, String memberId);
    public void removeHomeworkMember(String homeworkId, String memberId);
    public WorkMember getSingleMembers(String homeworkId, String memberId);
    public List<WorkMember> homeworkMembers(String homeworkId);
}
