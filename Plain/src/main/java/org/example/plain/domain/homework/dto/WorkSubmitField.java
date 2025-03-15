package org.example.plain.domain.homework.dto;

import lombok.Data;
import org.example.plain.domain.homework.entity.FileEntity;
import org.example.plain.domain.homework.entity.WorkSubmitFieldEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Data
public class WorkSubmitField {
    String workId;
    String userId;
    List<MultipartFile> file;
}
