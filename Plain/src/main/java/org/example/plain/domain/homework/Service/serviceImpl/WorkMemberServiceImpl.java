package org.example.plain.domain.homework.Service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.example.plain.domain.groupmember.entity.GroupMember;
import org.example.plain.domain.groupmember.entity.GroupMemberId;
import org.example.plain.domain.homework.Service.interfaces.WorkMemberService;
import org.example.plain.domain.homework.dto.WorkMember;
import org.example.plain.domain.homework.entity.WorkEntity;
import org.example.plain.domain.homework.entity.WorkMemberEntity;
import org.example.plain.repository.BoardRepository;
import org.example.plain.repository.GroupMemberRepository;
import org.example.plain.repository.WorkMemberRepository;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkMemberServiceImpl implements WorkMemberService {

    private final GroupMemberRepository groupMemberRepository;
    private final WorkMemberRepository workMemberRepository;
    private final BoardRepository boardRepository;

    @Override
    public void addHomeworkMember(String workId, String memberId) {
        WorkEntity work = boardRepository.findByWorkId(workId).orElseThrow();

        if (work.getUserId().equals(memberId)) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(400),"생성자에게 과제를 할당 할 수 없습니다.");
        }


        GroupMember groupMember = groupMemberRepository.findById(new GroupMemberId(work.getGroupId(), memberId)).orElseThrow();

        WorkMemberEntity workMemberEntity = WorkMemberEntity.makeWorkMemberEntity(groupMember.getUser(), work);

        System.out.println(workMemberEntity.getWork().getWorkId());
        System.out.println(workMemberEntity.getWorkMemberId().getWork());
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
