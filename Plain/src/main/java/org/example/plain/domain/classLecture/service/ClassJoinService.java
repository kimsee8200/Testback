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
public class ClassJoinService {

    private final ClassLectureRepositoryPort classLectureRepositoryPort;
    private final ClassMemberRepository classMemberRepository;
    private final UserRepository userRepository;

    /**
     * 클래스 코드로 가입
     * @param code
     * @param userId
     * @return
     */
    public String joinByCode(String code, String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        ClassLecture classLecture = classLectureRepositoryPort.findByCode(code);

        classLecture.addMember(user);
        classMemberRepository.save(new ClassMember(classLecture, user));

        return "클래스 코드 가입 완료" ;
    }

    /**
     * 클래스 가입
     * @param userId
     * @param classId
     */
    public void joinClass(String userId, String classId) {
        ClassLecture classLecture = classLectureRepositoryPort.findById(classId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        classLecture.addMember(user);
        classMemberRepository.save(new ClassMember(classLecture, user));
    }

}
