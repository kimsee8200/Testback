package org.example.plain.domain.homework.entity;

import jakarta.persistence.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Entity
public class FileEntity {
    @Id
    @ManyToOne
    WorkSubmitFieldEntity workSubmitFieldEntity;
    @Column
    File file;
}
