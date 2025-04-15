package org.example.plain.domain.file.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.plain.domain.user.entity.User;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Integer id;


    private String filename;

    @Column(name = "file_path")
    private String filePath;



    public FileEntity(String filename, String filePath) {
        this.filename = filename;
        this.filePath = filePath;
    }

    public static List<FileEntity> fileEntities(List<File> files) {
        List<FileEntity> fileEntities = new ArrayList<>();
        return fileEntities;
    }
}

