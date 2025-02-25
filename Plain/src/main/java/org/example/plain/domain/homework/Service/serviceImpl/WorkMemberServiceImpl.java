package org.example.plain.domain.homework.Service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.example.plain.domain.homework.Service.interfaces.WorkMemberService;
import org.example.plain.domain.homework.dto.WorkMember;
import org.example.plain.domain.homework.entity.WorkMemberEntity;
import org.example.plain.repository.BoardRepository;
import org.example.plain.repository.WorkMemberRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkMemberServiceImpl implements WorkMemberService {

    private final WorkMemberRepository workMemberRepository;
    private final BoardRepository boardRepository;

    @Override
    public void addHomeworkMember(String workId, String memberId) {
        WorkMemberEntity workMemberEntity = WorkMemberEntity.makeWorkMemberEntity(memberId, workId);
        workMemberRepository.save(workMemberEntity);
    }

    @Override
    public void removeHomeworkMember(String workId, String memberId) {

    }

    @Override
    public WorkMember getSingleMembers(String workId, String memberId) {
        return null;
    }

    @Override
    public List<WorkMember> homeworkMembers(String workId) {
        List<WorkMember> workMemberList = new ArrayList<>();
        for (WorkMemberEntity workMemberEntity:workMemberRepository.findByWork(boardRepository.findByWorkId(workId).orElseThrow())){
            workMemberList.add(WorkMember.changeEntity(workMemberEntity));
        }
        return workMemberList;
    }
}
