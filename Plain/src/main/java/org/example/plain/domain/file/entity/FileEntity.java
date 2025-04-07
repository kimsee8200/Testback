package org.example.plain.domain.file.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.plain.domain.homework.entity.WorkEntity;
import org.example.plain.domain.user.entity.User;
import org.example.plain.domain.homework.entity.WorkMemberEntity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "file", schema = "plain")
@AllArgsConstructor
@NoArgsConstructor
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Integer id;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "h_id", referencedColumnName = "h_id"),
        @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    })
    private WorkMemberEntity workMember;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    private String filename;

    @Column(name = "file_path")
    private String filePath;

    @Builder
    public FileEntity(String filename, String filePath, WorkMemberEntity workMember) {
        this.filename = filename;
        this.filePath = filePath;
        this.workMember = workMember;
        this.user = workMember.getUser();
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

