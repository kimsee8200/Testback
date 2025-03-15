package org.example.plain.domain.userManage.service;

import lombok.RequiredArgsConstructor;
import org.example.plain.domain.classLecture.dto.ClassResponse;
import org.example.plain.domain.groupmember.entity.GroupMember;
import org.example.plain.domain.lecture.normal.entity.Lecture;
import org.example.plain.domain.user.dto.UserRequest;
import org.example.plain.domain.user.dto.UserResponse;
import org.example.plain.domain.user.entity.User;
import org.example.plain.domain.user.repository.UserRepository;
import org.example.plain.domain.userManage.interfaces.UserManageService;
import org.example.plain.repository.GroupMemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserManageServiceImpl implements UserManageService {

    private final UserRepository userRepository;
    private final GroupMemberRepository groupMemberRepository;

    @Override
    public UserResponse userSingleInfo(String userId) {
        User user = userRepository.findById(userId).orElseThrow();
        UserResponse userResponse = UserResponse.chaingeUsertoUserResponse(user);
        return userResponse;
    }

    @Override
    @Transactional
    public List<ClassResponse> getMyClasses(String userId) {
        User user = userRepository.findById(userId).orElseThrow();
        List<GroupMember> groupMembers = groupMemberRepository.findAllByUser(user).orElseThrow();
        List<ClassResponse> classResponses = new ArrayList<>();
        for(GroupMember groupMember : groupMembers) {
            classResponses.add(ClassResponse.chaingeClassLectureToResponse(groupMember.getGroup()));
        }
        return classResponses;
    }

    @Override
    public List<Lecture> getMyLectures(String userId) {
        return List.of();
    }

    @Override
    public void readAlarm(boolean read) {

    }
}
