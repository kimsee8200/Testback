package org.example.plain.domain.groupmember.service;

import org.example.plain.domain.groupmember.dto.GroupMemberDTO;
import org.example.plain.domain.group.entity.Group;
import org.example.plain.domain.groupmember.entity.GroupMember;
import org.example.plain.domain.user.entity.User;
import org.example.plain.repository.GroupMemberRepository;
import org.example.plain.repository.GroupRepository;
import org.example.plain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupMemberServiceImpl implements GroupMemberService {

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<GroupMemberDTO> readGroupMemberAll(String groupId) {
        Group group = groupRepository.findById(groupId).orElse(null);
        List<GroupMember> groupMembers = groupMemberRepository.findAllByGroup(group);
        List<GroupMemberDTO> groupMembersDTO = new ArrayList<>();
        for (GroupMember groupMember : groupMembers) {
            groupMembersDTO.add(groupMember.toDTO());
        }
        return groupMembersDTO;
    }

    @Override
    public GroupMemberDTO readGroupMember(String groupId, String userId) {
        Group group = groupRepository.findById(groupId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);
        GroupMember groupMember = groupMemberRepository.findByGroupAndUser(group, user);
        return groupMember.toDTO();
    }

    @Override
    public void joinGroup(String groupId, String userId) {
        Group group = groupRepository.findById(groupId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);
        GroupMember groupMember = new GroupMember(group, user);
        groupMemberRepository.save(groupMember);
    }

    @Override
    public void quitGroup(String groupId, String userId) {
        Group group = groupRepository.findById(groupId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);
        groupMemberRepository.deleteByGroupAndUser(group, user);
    }
}
