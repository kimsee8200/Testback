package org.example.plain.domain.homework.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Data
public class WorkSubmitField {
    String workId;
    String userId;
    List<File> file;
}
