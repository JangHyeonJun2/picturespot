//package com.sparta.hanghae.picturespot.service;
//
//import com.amazonaws.auth.AWSCredentials;
//import com.amazonaws.auth.AWSStaticCredentialsProvider;
//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3ClientBuilder;
//import com.amazonaws.services.s3.model.CannedAccessControlList;
//import com.amazonaws.services.s3.model.PutObjectRequest;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.annotation.PostConstruct;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Optional;
//
//@Slf4j
//@Component
//public class S3Service {
//    private AmazonS3 amazonS3;
//
//    @Value("")
//    private String accessKey;
//
//    @Value("")
//    private String secretKey;
//
//    @Value("")
//    private String bucket;
//
//    @Value("ap-northeast-2")
//    private String region;
//
//    @PostConstruct
//    private void setAmazonS3() {
//        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
//        this.amazonS3 = AmazonS3ClientBuilder
//                .standard()
//                .withCredentials(new AWSStaticCredentialsProvider(credentials))
//                .withRegion(region)
//                .build();
//    }
//
//
//    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
//        File uploadFile = convert(multipartFile)
//                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));
//
//        return upload(uploadFile, dirName);
//    }
//
//    private String upload(File uploadFile, String dirName) {
//        String fileName = uploadFile.getName().replace(" ","");
//        Date date_now = new Date(System.currentTimeMillis()); // 현재시간을 가져와 Date형으로 저장한다
//        SimpleDateFormat fourteen_format = new SimpleDateFormat("yyyyMMddHHmmss");
//
//        String dateFileName = fourteen_format.format(date_now) + fileName;
//
//        String resultFileName = dirName + "/" + dateFileName;
//        String uploadImageUrl = putS3(uploadFile, resultFileName);
//        removeNewFile(uploadFile);
//        return uploadImageUrl;
//    }
//
//    private String putS3(File uploadFile, String fileName) {
//        amazonS3.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
//        return amazonS3.getUrl(bucket, fileName).toString();
//    }
//
//    private void removeNewFile(File targetFile) {
//        if (targetFile.delete()) {
//            log.info("파일이 삭제되었습니다.");
//        } else {
//            log.info("파일이 삭제되지 못했습니다.");
//        }
//    }
//
//    private Optional<File> convert(MultipartFile file) throws IOException {
//        File convertFile = new File(file.getOriginalFilename());
//        if(convertFile.createNewFile()) {
//            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
//                fos.write(file.getBytes());
//            }
//            return Optional.of(convertFile);
//        }
//
//        return Optional.empty();
//    }
//}
