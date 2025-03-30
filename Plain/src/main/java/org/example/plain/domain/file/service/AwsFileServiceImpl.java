package org.example.plain.domain.file.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.example.plain.domain.file.dto.FileData;
import org.example.plain.domain.file.dto.SubmitFileData;
import org.example.plain.domain.file.entity.FileEntity;
import org.example.plain.domain.file.interfaces.CloudFileService;
import org.example.plain.domain.file.interfaces.FileService;
import org.example.plain.domain.homework.repository.FileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AwsFileServiceImpl implements CloudFileService {

    @Value(value = "${file.path}")
    private String filePath;

    @Value(value = "${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    private final FileRepository fileRepository;

    // 버킷 파일 아이디 문제.
    public FileEntity uploadSingleFile(FileData fileData){
        SubmitFileData submitFileData = (SubmitFileData) fileData;

        String filename = makeFilename(
                submitFileData.getFileName(),submitFileData.getUserId().getId(),submitFileData.getWorkId().getBoardId());

        try {
            File fileCarry = new File(fileData.getFileName());
            submitFileData.getFile().transferTo(fileCarry);
            amazonS3.putObject(new PutObjectRequest(bucket, filename, fileCarry));
            fileCarry.delete();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String fileUrl = String.valueOf(amazonS3.getUrl(bucket, filename));

        return fileRepository.save(
                FileEntity.builder()
                .filename(submitFileData.getFileName())
                .filePath(fileUrl)
                .board(submitFileData.getWorkId())
                .user(submitFileData.getUserId())
                .build()
        );
    }

    @Override
    public List<FileEntity> uploadFiles(FileData fileData, List<MultipartFile> files) {
        SubmitFileData submitFileData = (SubmitFileData) fileData;
        List<FileEntity> fileEntities = new ArrayList<>();
        files.forEach(file -> {
            String filename = makeFilename(
                    submitFileData.getFileName(),submitFileData.getUserId().getId(),submitFileData.getWorkId().getBoardId());

            try {
                File fileCarry = new File(filePath+fileData.getFile().getOriginalFilename());
                submitFileData.getFile().transferTo(fileCarry);
                amazonS3.putObject(new PutObjectRequest(bucket, filename, fileCarry));
                fileCarry.delete();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String fileUrl = String.valueOf(amazonS3.getUrl(bucket, filename));

            fileEntities.add(
                    fileRepository.save(
                        FileEntity.builder()
                        .user(submitFileData.getUserId())
                        .board(submitFileData.getWorkId())
                        .filename(file.getOriginalFilename())
                        .filePath(fileUrl)
                        .build()
                    )
            );
        });
        return fileEntities;
    }

    @Override
    public void deleteFile(String filename) {
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, filename));
    }

    public String makeFilename(String originalFilename, String userId, String workId){
       return originalFilename+" "+userId+" "+workId;
    }

    public String makeFilename (String originalFilename, String userId){
        Integer count = 1;
        int split = originalFilename.lastIndexOf(".");
        String name = originalFilename.substring(0, split);
        String extending = originalFilename.substring(split);
        String addSide = "";

        while(new File(userId+name+addSide+extending).exists()){
            addSide = "(" + count + ")";
            count++;
        }
        return userId+name+addSide+extending;
    }

    public String makeFilename (String originalFilename){
        Integer count = 1;
        int split = originalFilename.lastIndexOf(".");
        String name = originalFilename.substring(0, split);
        String extending = originalFilename.substring(split);
        String addSide = "";

        while(new File(name+addSide+extending).exists()){
            addSide = "(" + count + ")";
            count++;
        }
        return name+addSide+extending;
    }
}
