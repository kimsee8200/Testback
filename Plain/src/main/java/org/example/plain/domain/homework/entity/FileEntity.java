package org.example.plain.domain.homework.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.example.plain.domain.board.entity.BoardEntity;
import org.example.plain.domain.user.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "file")
public class FileEntity{

    @EmbeddedId
    private FileEntityKey id;

    @MapsId("assignmentId")
    @ManyToOne
    @JoinColumn(name = "h_id")
    private WorkEntity board;

    @MapsId("userId")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String filename;

    @Column(name = "file_path")
    private String filePath;

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

