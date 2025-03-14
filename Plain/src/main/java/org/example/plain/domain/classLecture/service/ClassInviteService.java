package org.example.plain.domain.classLecture.service;

import lombok.RequiredArgsConstructor;
import org.example.plain.domain.classLecture.entity.ClassLecture;
import org.example.plain.domain.classLecture.repository.ClassLectureRepositoryPort;
import org.example.plain.domain.classMember.entity.ClassMember;
import org.example.plain.domain.classMember.repository.ClassMemberRepository;
import org.example.plain.domain.user.entity.User;
import org.example.plain.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClassInviteService {

    private final ClassLectureRepositoryPort classLectureRepositoryPort;
    private final ClassMemberRepository classMemberRepository;
    private final UserRepository userRepository;

    public String joinByCode(String id) {
        ClassLecture classLecture = classLectureRepositoryPort.findById(id);

        return "https://www.example.com/invite?code=" + classLecture.getCode();
    }

    public void joinClass(String userId, String classId) {
        ClassLecture classLecture = classLectureRepositoryPort.findById(classId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        classLecture.addMember(user);
        classMemberRepository.save(new ClassMember(classLecture, user));
    }
}
