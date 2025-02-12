package org.example.plain.domain.classLecture.repository;

import org.example.plain.domain.classLecture.entity.ClassLecture;

import java.util.List;

public interface ClassLectureRepositoryPort {
    ClassLecture save(ClassLecture classLecture);

    ClassLecture findById(Long id);

    List<ClassLecture> findAll();

    void delete(Long id);
}
