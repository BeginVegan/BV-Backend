package com.beginvegan.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@RequiredArgsConstructor    // final 멤버변수가 있으면 생성자 항목에 포함시킴
@Component
@Service
public class S3Service {

    @Autowired
    private final AmazonS3 amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // MultipartFile을 전달받아 File로 전환한 후 S3에 업로드
    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환 실패"));
        return upload(uploadFile, dirName);
    }

    public List<String> uploadMulti(List<MultipartFile> multipartFiles, String dirName) throws IOException {
        List<String> fileNames = new ArrayList<>();
        for (int index = 0; index < multipartFiles.size(); index++) {
            MultipartFile multipartFile = multipartFiles.get(index);
            File uploadFile = convert(multipartFile)
                    .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File conversion failed"));
            fileNames.add(upload(uploadFile, dirName, index));
        }
        return fileNames;
    }

    private String upload(File uploadFile, String dirName) {
        String fileName = dirName + "/" + uploadFile.getName();
        String uploadImageUrl = putS3(uploadFile, fileName);

        removeNewFile(uploadFile);  // 로컬에 생성된 File 삭제 (MultipartFile -> File 전환 하며 로컬에 파일 생성됨)

        return uploadImageUrl;      // 업로드된 파일의 S3 URL 주소 반환
    }

    private String upload(File uploadFile, String dirName, int index) {
        String fileName = uploadFile.getName();
        String fileExtension = "";

        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            fileExtension = fileName.substring(dotIndex + 1);
        }

        String uploadFileName = dirName + "/" + index + fileExtension;
        String uploadImageUrl = putS3(uploadFile, uploadFileName);

        removeNewFile(uploadFile);  // 로컬에 생성된 File 삭제 (MultipartFile -> File 전환 하며 로컬에 파일 생성됨)

        return uploadImageUrl;      // 업로드된 파일의 S3 URL 주소 반환
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(
                new PutObjectRequest(bucket, fileName, uploadFile)
                        .withCannedAcl(CannedAccessControlList.PublicRead)	// PublicRead 권한으로 업로드 됨
        );
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    public List<String> getS3(String prefix) {
        List<String> fileNames = new ArrayList<>();
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
        listObjectsRequest.setBucketName(bucket);
        if (!prefix.equals("")) {
            listObjectsRequest.setPrefix(prefix);
        }

        ObjectListing s3Objects;
        do {
            s3Objects = amazonS3Client.listObjects(listObjectsRequest);
            for (S3ObjectSummary s3ObjectSummary : s3Objects.getObjectSummaries()) {
                fileNames.add(s3ObjectSummary.getKey());
            }
            listObjectsRequest.setMarker(s3Objects.getNextMarker());
        } while (s3Objects.isTruncated());

        return fileNames;
    }

    public void removeS3(String key) {
        amazonS3Client.deleteObject(bucket, key);
    }

    public void removeFolderS3(String folderName) {
        List<String> storedFileNames = this.getS3(folderName);
        System.out.println("storedFileName = " + storedFileNames);

        for (String key : storedFileNames)
            amazonS3Client.deleteObject(bucket, key);
    }

    private void removeNewFile(File targetFile) {
        if(targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        }else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

    private Optional<File> convert(MultipartFile file) throws  IOException {
        log.info(file.getOriginalFilename());
        File convertFile = new File(file.getOriginalFilename());
        if(convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }

    public List<String> getRestaurantImages(String prefix) {
        List<String> fileNames = new ArrayList<>();
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
        listObjectsRequest.setBucketName(bucket);
        if (!prefix.equals("")) {
            listObjectsRequest.setPrefix(prefix);
        }

        ObjectListing s3Objects;
        do {
            s3Objects = amazonS3Client.listObjects(listObjectsRequest);
            for (S3ObjectSummary s3ObjectSummary : s3Objects.getObjectSummaries()) {
                fileNames.add("https://bv-image.s3.ap-northeast-2.amazonaws.com/" + s3ObjectSummary.getKey());
            }
            listObjectsRequest.setMarker(s3Objects.getNextMarker());
        } while (s3Objects.isTruncated());

        return fileNames;
    }

    public ByteArrayOutputStream downloadImage(String pathName) throws IOException {
        // 파일 유무 확인
        List<String> fileNames = getS3(pathName);

        // 바이트를 ZIP 파일로 바꾸기 위한 변수들
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        ZipOutputStream zipOut = new ZipOutputStream(byteArray);

        // 폴더안의 파일을 하나씩 zipOut에 추가
        for(String fileName : fileNames) {
            addFileToZip(zipOut, fileName, getS3ObjectInputStream(fileName));
        }

        // ZipOutputStream 종료
        zipOut.close();

        // 바이트 리턴
        return byteArray;
    }

    private S3ObjectInputStream getS3ObjectInputStream(String fileName) {
        S3Object s3Object = amazonS3Client.getObject(bucket, fileName);
        return s3Object.getObjectContent();
    }

    private void addFileToZip(ZipOutputStream zipOut, String fileName, S3ObjectInputStream s3ObjectInputStream) throws IOException {
        // ZipEntry 생성
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);

        // S3ObjectInputStream을 ZipOutputStream에 쓰기
        IOUtils.copy(s3ObjectInputStream, zipOut);

        // closeEntry 종료
        zipOut.closeEntry();

        // S3ObjectInputStream 종료
        s3ObjectInputStream.close();
    }
}