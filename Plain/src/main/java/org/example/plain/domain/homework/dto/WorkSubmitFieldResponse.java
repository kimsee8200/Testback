package org.example.plain.domain.homework.dto;

import lombok.Data;
import org.example.plain.domain.homework.entity.FileEntity;
import org.example.plain.domain.homework.entity.WorkSubmitFieldEntity;
import org.example.plain.repository.WorkSubmitFieldRepository;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.util.List;


@Data
public class WorkSubmitFieldResponse {
    String workId;
    String userId;
    List<File> file;

    public static WorkSubmitFieldResponse changeEntity(WorkSubmitFieldEntity workSubmitFieldEntity) {

        WorkSubmitFieldResponse workSubmitField = new WorkSubmitFieldResponse();
        workSubmitField.setWorkId(workSubmitFieldEntity.getWorkId().getWorkId());
        workSubmitField.setUserId(workSubmitFieldEntity.getUserId().getId());
        return workSubmitField;
    }
}


