//package org.example.plain.domain.classMember.service;
//
//import org.example.plain.domain.classLecture.entity.ClassLecture;
//import org.example.plain.domain.classLecture.repository.ClassLectureRepositoryPort;
//import org.example.plain.domain.classMember.dto.GroupMemberDTO;
//import org.example.plain.domain.classMember.entity.ClassMember;
//import org.example.plain.domain.user.entity.User;
//import org.example.plain.domain.user.repository.UserRepository;
//import org.example.plain.repository.GroupMemberRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class GroupMemberServiceImpl implements GroupMemberService {
//
//    @Autowired
//    private GroupMemberRepository groupMemberRepository;
//
//    @Autowired
//    private ClassLectureRepositoryPort groupRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public List<GroupMemberDTO> readGroupMemberAll(String groupId) {
//        ClassLecture group = groupRepository.findById(groupId);
//        List<ClassMember> classMembers = groupMemberRepository.findAllByClassLecture(group);
//        List<GroupMemberDTO> groupMembersDTO = new ArrayList<>();
//        for (ClassMember classMember : classMembers) {
//            groupMembersDTO.add(classMember.toDTO());
//        }
//        return groupMembersDTO;
//    }
//
//    @Override
//    public GroupMemberDTO readGroupMember(String groupId, String userId) {
//        ClassLecture group = groupRepository.findById(groupId);
//        User user = userRepository.findById(userId).orElse(null);
//        ClassMember classMember = groupMemberRepository.findByClassLectureAndUser(group, user);
//        return classMember.toDTO();
//    }
//
//    @Override
//    public void joinGroup(String groupId, String userId) {
//        ClassLecture group = groupRepository.findById(groupId);
//        User user = userRepository.findById(userId).orElse(null);
//        ClassMember classMember = new ClassMember(null,group, user);
//        groupMemberRepository.save(classMember);
//    }
//
//    @Override
//    public void quitGroup(String groupId, String userId) {
//        ClassLecture group = groupRepository.findById(groupId);
//        User user = userRepository.findById(userId).orElse(null);
//        groupMemberRepository.deleteByClassLectureAndUser(group, user);
//    }
//}
