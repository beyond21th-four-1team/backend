package com.mac4.yeopabackend.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import com.mac4.yeopabackend.post.dto.FileInfo;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileService {
    private final S3Client s3Client;

    private final String bucket = "yeopa";

    // ✅ 업로드
    public FileInfo uploadFile(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("file is null or empty");
        }
        String fileName = file.getOriginalFilename();
        if(fileName == null || fileName.isBlank()){
            throw new IllegalArgumentException("file name is null or blank");
        }
        String key = UUID.randomUUID().toString();
        String rewriteFileName = key + file.getOriginalFilename();
        byte[] bytes = file.getBytes();

        String contentType = file.getContentType();
        if(contentType == null || contentType.isBlank()){
            contentType = "application/octet-stream";
        }

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucket)
                .key(rewriteFileName)
                .contentType(contentType)
                .build();

        s3Client.putObject(request, RequestBody.fromBytes(bytes));

        return new FileInfo(
                key,
                fileName,
                file.getContentType(),
                file.getSize()
        );
    }
    // ✅ 파일 목록 조회
    public List<String> listFiles() {
        ListObjectsV2Response response = s3Client.listObjectsV2(
                ListObjectsV2Request.builder().bucket(bucket).build()
        );
        return response.contents().stream()
                .map(S3Object::key)
                .collect(Collectors.toList());
    }

    // ✅ 삭제
    public void deleteFile(String fileName) {
        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(fileName)
                .build();
        s3Client.deleteObject(request);
    }
}