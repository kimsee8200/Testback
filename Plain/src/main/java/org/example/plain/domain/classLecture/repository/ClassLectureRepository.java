package org.example.plain.domain.classLecture.repository;

import org.example.plain.domain.classLecture.entity.ClassLecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassLectureRepository extends JpaRepository<ClassLecture, String> {

    ClassLecture findByCode(String code);
}
