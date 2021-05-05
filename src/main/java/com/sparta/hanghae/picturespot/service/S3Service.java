package com.sparta.hanghae.picturespot.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3Service {
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String[] upload(List<MultipartFile> multipartFile, String dirName) throws IOException {
        File[] uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));

        return upload(uploadFile, dirName);
    }

    //profile
    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));

        return upload(uploadFile, dirName);
    }

    private String[] upload(File[] uploadFile, String dirName) {
        String[] uploadImgUrl = new String[uploadFile.length];

        for (int i=0; i<uploadFile.length; i++) {
//            String fileName = uploadFile[i].getName().replace(" ", "");
            String fileName = uploadFile[i].getName().substring(uploadFile[i].getName().lastIndexOf('.'));
            Date date_now = new Date(System.currentTimeMillis()); // 현재시간을 가져와 Date형으로 저장한다
            SimpleDateFormat fourteen_format = new SimpleDateFormat("yyyyMMddHHmmss");

            String dateFileName = fourteen_format.format(date_now) + fileName;
            String resultFileName = dirName + "/" + dateFileName;
            uploadImgUrl[i] = putS3(uploadFile[i],resultFileName);
            removeNewFile(uploadFile[i]);
        }
        return uploadImgUrl;
    }

    //profile
    private String upload(File uploadFile, String dirName) {

        String fileName = uploadFile.getName().substring(uploadFile.getName().lastIndexOf('.'));
        Date date_now = new Date(System.currentTimeMillis()); // 현재시간을 가져와 Date형으로 저장한다
        SimpleDateFormat fourteen_format = new SimpleDateFormat("yyyyMMddHHmmss");

        String dateFileName = fourteen_format.format(date_now) + fileName;
        String resultFileName = dirName + "/" + dateFileName;
        String uploadImgUrl = putS3(uploadFile,resultFileName);
        removeNewFile(uploadFile);

        return uploadImgUrl;
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));

        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

    private Optional<File[]> convert(List<MultipartFile> file) throws IOException {
        File[] convertFiles = new File[file.size()];
        for (int i=0; i<file.size(); i++) {
            convertFiles[i] = new File(file.get(i).getOriginalFilename());
            if (!convertFiles[i].createNewFile()) {
                return Optional.empty();
            }else {
                try(FileOutputStream fos = new FileOutputStream(convertFiles[i])) {
                    fos.write(file.get(i).getBytes());
                }
            }
        }
        return Optional.of(convertFiles);

//        File convertFile = new File(file.getOriginalFilename());
//        if(convertFile.createNewFile()) {
//            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
//                fos.write(file.getBytes());
//            }
//            return Optional.of(convertFile);
//        }
//
//        return Optional.empty();
    }

    //profile
    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        if (!convertFile.createNewFile()) {
                return Optional.empty();
        }else {
            try(FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
        }
        return Optional.of(convertFile);
    }
}