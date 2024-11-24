/*
package com.dyks.aihubbe.domain.shared;

import io.minio.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

import com.dyks.aihubbe.domain.user.exception.InvalidFileFormat;
import com.dyks.aihubbe.global.error.exception.custom.DeleteForbiddenException;

@Service
@Slf4j
@RequiredArgsConstructor
public class MinioService {

	private final MinioClient minioClient;

	@Value("${minio.bucket-name}")
	private String bucketName;

	@Value("${minio.url}")
	private String minioUrl;

	// 버킷 생성
	public void createBucket() throws Exception {
		boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
		if (!isExist) {
			minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
		}
	}
	*/
/**
	 * 이미지 업로드
	 *//*

	public String upload(MultipartFile multipartFile, String dirName) {
		String fileName = multipartFile.getOriginalFilename();

		// 파일 확장자 검증
		if (!(fileName.toLowerCase().endsWith(".png") || fileName.toLowerCase().endsWith(".jpg") ||
			fileName.toLowerCase().endsWith(".jpeg") || fileName.toLowerCase().endsWith(".gif") ||
			fileName.toLowerCase().endsWith(".bmp"))) {
			throw new InvalidFileFormat();
		}

		// 고유 파일 이름 생성
		String uniqueFileName = UUID.randomUUID() + "-" + fileName;
		String filePath = dirName + "/" + uniqueFileName;

		try {
			// MinIO에 파일 업로드
			minioClient.putObject(
				PutObjectArgs.builder()
					.bucket(bucketName)
					.object(filePath)
					.stream(multipartFile.getInputStream(), multipartFile.getSize(), -1)
					.contentType(multipartFile.getContentType())
					.build()
			);

			// MinIO의 파일 URL 반환
			return getFileUrl(filePath);
		} catch (Exception e) {
			log.error("파일 업로드 실패: {}", e.getMessage());
			throw new RuntimeException();
		}
	}
	// 파일 URL 생성
	private String getFileUrl(String filePath) {
		return String.format("%s/%s/%s", minioUrl, bucketName, filePath);
	}

	// 파일 삭제
	public void removeFile(String fileUrl, String filePath) {
		try {
			String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
			String fullPath = filePath.endsWith("/") ? filePath + fileName : filePath + "/" + fileName;

			minioClient.removeObject(
				RemoveObjectArgs.builder()
					.bucket(bucketName)
					.object(fullPath)
					.build()
			);
			log.info("파일 삭제 성공: {}", fullPath);
		} catch (Exception e) {
			log.error("파일 삭제 실패: {}", e.getMessage());
			throw new DeleteForbiddenException();
		}
	}
}
*/
