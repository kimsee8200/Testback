package org.example.plain.domain.file.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.plain.domain.homework.entity.WorkEntity;
import org.example.plain.domain.user.entity.User;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "file")
@AllArgsConstructor
@NoArgsConstructor
public class FileEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "h_id")
    private WorkEntity board;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String filename;

    @Column(name = "file_path")
    private String filePath;

    @Builder
    public FileEntity(String filename, String filePath, WorkEntity board, User user) {
        this.filename = filename;
        this.filePath = filePath;
        this.board = board;
        this.user = user;
    }

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

