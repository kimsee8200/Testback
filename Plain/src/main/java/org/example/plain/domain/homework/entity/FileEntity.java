package org.example.plain.domain.homework.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class FileEntity extends WorkSubmitFieldEntity{
    @Column
    String filename;

    public static List<FileEntity> fileEntities(List<File> files) {
        List<FileEntity> fileEntities = new ArrayList<>();
        for (File file : files) {
            FileEntity fileEntity = new FileEntity();
            fileEntity.setFilename(file.getName());
            fileEntities.add(fileEntity);
        }
        return fileEntities;
    }
}
