package org.example.plain.service.impl;

import org.example.plain.dto.GroupMemberDTO;
import org.example.plain.entity.Group;
import org.example.plain.entity.GroupMember;
import org.example.plain.entity.User;
import org.example.plain.repository.GroupMemberRepository;
import org.example.plain.service.interfaces.GroupMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupMemberServiceImpl implements GroupMemberService {

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    @Override
    public List<GroupMemberDTO> readGroupMemberAll(Group group) {
        List<GroupMember> groupMembers = groupMemberRepository.findAllByGroup(group);
        List<GroupMemberDTO> groupMembersDTO = new ArrayList<>();
        for (GroupMember groupMember : groupMembers) {
            groupMembersDTO.add(groupMember.toDTO());
        }
        return groupMembersDTO;
    }

    @Override
    public GroupMemberDTO readGroupMember(Group group, User user) {
        GroupMember groupMember = groupMemberRepository.findByGroupAndUser(group, user);
        return groupMember.toDTO();
    }

    @Override
    public void joinGroup(Group group, User user) {
        GroupMember groupMember = new GroupMember(group, user);
        groupMemberRepository.save(groupMember);
    }

    @Override
    public void quitGroup(Group group, User user) {
        groupMemberRepository.deleteByGroupAndUser(group, user);
    }
}
