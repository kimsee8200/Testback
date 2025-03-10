package org.example.plain.domain.groupmember.service;

import org.example.plain.domain.classLecture.entity.ClassLecture;
import org.example.plain.domain.classLecture.repository.ClassLectureRepositoryPort;
import org.example.plain.domain.groupmember.dto.GroupMemberDTO;
import org.example.plain.domain.groupmember.entity.GroupMember;
import org.example.plain.domain.groupmember.entity.GroupMemberId;
import org.example.plain.domain.user.dto.CustomUserDetails;
import org.example.plain.domain.user.entity.User;
import org.example.plain.domain.user.repository.UserRepository;
import org.example.plain.repository.GroupMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupMemberServiceImpl implements GroupMemberService {

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    @Autowired
    private ClassLectureRepositoryPort groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<GroupMemberDTO> readGroupMemberAll(String groupId) {
        ClassLecture group = groupRepository.findById(groupId);
        List<GroupMember> groupMembers = groupMemberRepository.findAllByGroup(group);
        List<GroupMemberDTO> groupMembersDTO = new ArrayList<>();
        for (GroupMember groupMember : groupMembers) {
            groupMembersDTO.add(groupMember.toDTO());
        }
        return groupMembersDTO;
    }

    @Override
    public GroupMemberDTO readGroupMember(String groupId, String userId) {
        ClassLecture group = groupRepository.findById(groupId);
        User user = userRepository.findById(userId).orElse(null);
        GroupMember groupMember = groupMemberRepository.findByGroupAndUser(group, user);
        return groupMember.toDTO();
    }

    @Override
    public void joinGroup(String groupId, String userId) {
        ClassLecture group = groupRepository.findById(groupId);
        User user = userRepository.findById(userId).orElse(null);
        GroupMember groupMember = GroupMember.makeGroupMember(group, user);
        groupMemberRepository.save(groupMember);
    }

    @Override
    public void upgradeMemberRole(String groupId, String userId, String role, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        GroupMember groupMember = groupMemberRepository.findById(new GroupMemberId(groupId, userDetails.getUser().getId())).orElseThrow();

        // spring security 적용 가능.
        if (!groupMember.getAuthority().equals("LEADER")){
            throw new HttpClientErrorException(HttpStatusCode.valueOf(403),"접근 권한자가 아닙니다");
        }

        GroupMember groupMember1 = groupMemberRepository.findById(new GroupMemberId(groupId, userId)).orElseThrow();

        if (!groupMember1.getAuthority().equals("LEADER")){ // enum으로 대체 필요.
            groupMember1.setAuthority("LEADER");
            groupMemberRepository.save(groupMember1);
        } else throw new HttpClientErrorException(HttpStatusCode.valueOf(400),"이미 권한을 가지고 있습니다.");
    }

    @Override
    public void quitGroup(String groupId, String userId) {
        ClassLecture group = groupRepository.findById(groupId);
        User user = userRepository.findById(userId).orElse(null);
        groupMemberRepository.deleteByGroupAndUser(group, user);
    }
}
