package org.example.plain.domain.classLecture.service;

import lombok.RequiredArgsConstructor;
import org.example.plain.domain.classLecture.dto.ClassAddRequest;
import org.example.plain.domain.classLecture.dto.ClassResponse;
import org.example.plain.domain.classLecture.entity.ClassLecture;
import org.example.plain.domain.classLecture.repository.ClassLectureRepositoryPort;
import org.example.plain.domain.classLecture.util.CodeGenerator;
import org.example.plain.domain.user.entity.User;
import org.example.plain.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClassLectureService {

    private final ClassLectureRepositoryPort classLectureRepositoryPort;
    private final UserRepository userRepository;

    public ClassResponse createClass(ClassAddRequest classAddRequest) {
        User user = userRepository.getReferenceById(classAddRequest.user().getUserId());

        String title = classAddRequest.title();
        String description = classAddRequest.description();
        String code = generateCode();

        ClassLecture classLecture = classAddRequest.toEntity(user, code);
        classLectureRepositoryPort.save(classLecture);

        return ClassResponse.builder()
                .id(classLecture.getId())
                .title(title)
                .description(description)
                .code(code)
                .build();
    }


    public String generateCode() {
        return CodeGenerator.generateCode();
    }
}

