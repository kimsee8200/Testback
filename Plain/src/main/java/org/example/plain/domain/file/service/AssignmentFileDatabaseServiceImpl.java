package org.example.plain.domain.file.service;

import lombok.RequiredArgsConstructor;
import org.example.plain.domain.file.dto.FileData;
import org.example.plain.domain.file.dto.SubmitFileData;
import org.example.plain.domain.file.entity.FileEntity;
import org.example.plain.domain.file.entity.WorkFileEntity;
import org.example.plain.domain.file.interfaces.FileDatabaseService;
import org.example.plain.domain.homework.entity.WorkMemberEntity;
import org.example.plain.domain.file.repository.FileRepository;
import org.example.plain.repository.WorkMemberRepository;
import org.springframework.stereotype.Component;

@Component(value = "assignmentDatabase")
@RequiredArgsConstructor
public class AssignmentFileDatabaseServiceImpl implements FileDatabaseService {

    private final FileRepository fileRepository;
    private final WorkMemberRepository workMemberRepository;

    @Override
    public FileEntity save(String filename, String filepath, FileData fileData) {
        SubmitFileData submitFileData = fileData instanceof SubmitFileData ? (SubmitFileData) fileData : null;

        if (submitFileData.getUserId() == null || submitFileData.getWorkId() == null) {
            throw new IllegalArgumentException("User ID and Work ID cannot be null");
        }

        WorkMemberEntity workMember = workMemberRepository.findByWorkIdAndUserId(
                submitFileData.getWorkId().getWorkId(),
                submitFileData.getUserId().getId()
        ).orElseThrow(() -> new IllegalArgumentException("WorkMember not found"));

        return fileRepository.save(
                WorkFileEntity.builder()
                        .filename(filename)
                        .filePath(filepath)
                        .workMember(workMember)
                        .build()
        );
    }

    @Override
    public void chackFileData(FileData fileData) {
        SubmitFileData submitFileData = fileData instanceof SubmitFileData ? (SubmitFileData) fileData : null;

        if (submitFileData.getUserId() == null || submitFileData.getWorkId() == null) {
            throw new IllegalArgumentException("User ID and Work ID cannot be null");
        }
    }

    @Override
    public void delete(FileEntity file) {
        fileRepository.delete(file);
    }

    @Override
    public void delete(String filename, String filepath) {

    }


}
