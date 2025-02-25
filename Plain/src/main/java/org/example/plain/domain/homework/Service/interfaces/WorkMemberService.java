package org.example.plain.domain.homework.Service.interfaces;

import org.example.plain.domain.homework.dto.WorkMember;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WorkMemberService {
    public void addHomeworkMember(String workId, String memberId);
    public void removeHomeworkMember(String workId, String memberId);
    public WorkMember getSingleMembers(String workId, String memberId);
    public List<WorkMember> homeworkMembers(String workId);
}
