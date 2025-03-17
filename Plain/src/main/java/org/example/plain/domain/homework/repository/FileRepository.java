package org.example.plain.domain.homework.repository;

import org.example.plain.domain.homework.entity.FileEntity;
import org.example.plain.domain.homework.entity.FileEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, FileEntityKey> {
}
