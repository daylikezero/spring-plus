package org.example.expert.domain.common.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.common.exception.ServerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.exception.SdkServiceException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {
    private final S3Client s3Client;

    @Getter
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    /**
     * S3 이미지 업로드
     *
     * @param multipartFile
     * @return url
     */
    public String upload(MultipartFile multipartFile) {
        String fileName = UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();

        List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png", "gif");

        try {
            String originalFilename = multipartFile.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();

            if (!allowedExtensions.contains(fileExtension)) {
                throw new InvalidRequestException("파일 확장자 오류: Unsupported file format");
            }

            long maxSize = 1024 * 1024 * 5;
            if (multipartFile.getSize() > maxSize) {
                throw new InvalidRequestException("파일 크기 초과");
            }

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(fileName)
                    .contentType(multipartFile.getContentType())
                    .contentLength(multipartFile.getSize())
                    .build();

            // S3에 파일 업로드
            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(multipartFile.getBytes()));
        } catch (IOException e) {
            log.error(String.valueOf(e.getCause()));
            throw new ServerException("서버 오류: Failed to upload file");
        }

        return getPublicUrl(fileName);
    }

    /**
     * S3 이미지 삭제
     *
     * @param objectPath
     */
    public void delete(String objectPath) {
        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucket)
                    .key(objectPath)
                    .build();
            s3Client.deleteObject(deleteObjectRequest);
        } catch (SdkServiceException e) {
            log.error(String.valueOf(e.getCause()));
        }
    }

    private String getPublicUrl(String fileName) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucket, region, fileName);
    }
}
