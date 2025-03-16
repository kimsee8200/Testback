package org.example.plain.domain.lecture.normal.repository;

import org.example.plain.domain.lecture.normal.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lecture, String> {
}
