package org.example.plain.domain.groupmember.service;

import org.example.plain.domain.classLecture.entity.ClassLecture;
import org.example.plain.domain.classLecture.repository.ClassLectureRepositoryPort;
import org.example.plain.domain.groupmember.dto.GroupMemberDTO;
import org.example.plain.domain.groupmember.entity.GroupMember;
import org.example.plain.domain.user.entity.User;
import org.example.plain.domain.user.repository.UserRepository;
import org.example.plain.repository.GroupMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        List<GroupMember> groupMembers = groupMemberRepository.findAllByClassLecture(group);
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
        GroupMember groupMember = groupMemberRepository.findByClassLectureAndUser(group, user);
        return groupMember.toDTO();
    }

    @Override
    public void joinGroup(String groupId, String userId) {
        ClassLecture group = groupRepository.findById(groupId);
        User user = userRepository.findById(userId).orElse(null);
        GroupMember groupMember = new GroupMember(null,group, user);
        groupMemberRepository.save(groupMember);
    }

    @Override
    public void quitGroup(String groupId, String userId) {
        ClassLecture group = groupRepository.findById(groupId);
        User user = userRepository.findById(userId).orElse(null);
        groupMemberRepository.deleteByClassLectureAndUser(group, user);
    }
}
