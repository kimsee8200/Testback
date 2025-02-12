package org.example.plain.domain.classLecture.repository;

import lombok.RequiredArgsConstructor;
import org.example.plain.domain.classLecture.entity.ClassLecture;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ClassLectureRepositoryAdapter implements ClassLectureRepositoryPort {

    private final ClassLectureRepository classLectureRepository;

    @Override
    public ClassLecture save(ClassLecture classLecture) {
        return classLectureRepository.save(classLecture);
    }

    @Override
    public ClassLecture findById(Long id) {
        return classLectureRepository.findById(Long.valueOf(id))
                .orElseThrow();
    }

    @Override
    public List<ClassLecture> findAll() {
        return classLectureRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        ClassLecture classLecture = findById(id);
        classLectureRepository.delete(classLecture);
    }
}
