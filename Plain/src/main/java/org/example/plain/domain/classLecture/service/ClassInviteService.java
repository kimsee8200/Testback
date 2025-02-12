package org.example.plain.domain.classLecture.service;

import lombok.RequiredArgsConstructor;
import org.example.plain.domain.classLecture.entity.ClassLecture;
import org.example.plain.domain.classLecture.repository.ClassLectureRepositoryPort;
import org.example.plain.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClassInviteService {

    private final ClassLectureRepositoryPort classLectureRepositoryPort;
    private final UserRepository userRepository;

    public String joinByCode(Long id) {
        ClassLecture classLecture = classLectureRepositoryPort.findById(id);
        String code = classLecture.getCode();

        return "https://www.example.com/invite?code=" + code;
    }
}
