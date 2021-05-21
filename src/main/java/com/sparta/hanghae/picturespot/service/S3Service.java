package com.sparta.hanghae.picturespot.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sparta.hanghae.picturespot.model.BoardImgUrls;
import com.sparta.hanghae.picturespot.repository.BoardImgUrlsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {
    private  AmazonS3 amazonS3;
    private final BoardImgUrlsRepository boardImgUrlsRepository;
    @Value("AKIA4PAY5UU4VMHS4TMJ")
    private String accessKey;

    @Value("0i9ohKefHJctq5+dO3qg2Wakj3ebMSVrDU3dPoSS")
    private String secretKey;

    @Value("picturespot")
    private String bucket;

    @Value("ap-northeast-2")
    private String region;

    @PostConstruct
    public void setS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

        amazonS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.region)
                .build();
    }



//    @Value("${cloud.aws.s3.bucket}")
//    private String bucket;

    public String[] upload(List<MultipartFile> multipartFile, String dirName) throws IOException {
        //System.out.println("확인!!");
        File[] uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));

        return upload(uploadFile, dirName);
    }



    private String[] upload(File[] uploadFile, String dirName) {
        String[] uploadImgUrl = new String[uploadFile.length];


        for (int i=0; i<uploadFile.length; i++) {
//            String fileName = uploadFile[i].getName().replace(" ", "");
            String fileName = uploadFile[i].getName().substring(uploadFile[i].getName().lastIndexOf('.'));
            Date date_now = new Date(System.currentTimeMillis()); // 현재시간을 가져와 Date형으로 저장한다
            //파일 이름을 다르게 한다. 날짜로만헀는데 for문이 너무 빠르게 돌아서 mmss까지 커버가 안되서 교체!
            UUID uuid = UUID.randomUUID();
            String subUUID = uuid.toString().substring(0, 8);
            SimpleDateFormat fourteen_format = new SimpleDateFormat("yyyyMMddHHmmss");

            String dateFileName = fourteen_format.format(date_now) + fileName;
            String resultFileName = dirName + "/" + subUUID +dateFileName;
            uploadImgUrl[i] = putS3(uploadFile[i],resultFileName);
            removeNewFile(uploadFile[i]);
        }
        return uploadImgUrl;
    }

    private Optional<File[]> convert(List<MultipartFile> file) throws IOException {
        File[] convertFiles = new File[file.size()];
        for (int i=0; i<file.size(); i++) {
            convertFiles[i] = new File((file.get(i).getOriginalFilename()));
            convertFiles[i].mkdirs();
            Runtime.getRuntime().exec("chmod 777 " + file.get(i).getOriginalFilename());
            convertFiles[i].setExecutable(true, false);
            convertFiles[i].setReadable(true, false);
            convertFiles[i].setWritable(true, false);

            if (!convertFiles[i].createNewFile()) {
                return Optional.empty();
            }else {
                try(FileOutputStream fos = new FileOutputStream(convertFiles[i])) {
                    fos.write(file.get(i).getBytes());
                }
            }
        }
        return Optional.of(convertFiles);
    }

    //bucket에 있는 imgUrl 삭제하는 메서드.
    public void delete(BoardImgUrls[] boardImgUrls, String dirName) throws IOException {
        String[] imgUrls = new String[boardImgUrls.length];
        for (int i=0; i<boardImgUrls.length; i++) {
            imgUrls[i] = boardImgUrls[i].getImgUrl().substring(boardImgUrls[i].getImgUrl().lastIndexOf("/")+1);
        }

        for (String imgUrl : imgUrls) {
            String objkeyArr = dirName + "/" +imgUrl;
            DeleteObjectsRequest delObjReq = new DeleteObjectsRequest(bucket).withKeys(objkeyArr);
            amazonS3.deleteObjects(delObjReq);
        }
    }

    //profile
    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));

        return upload(uploadFile, dirName);
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



    private String putS3(File uploadFile, String fileName) {
        amazonS3.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));

        return amazonS3.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

    //삭제할 이미지를 id로 찾고 delete 메서드 호출
    public void findImgUrls(Long[] deleteImages) throws IOException {
        BoardImgUrls[] imgUrls = new BoardImgUrls[deleteImages.length]; //id로 찾은 boardImgUrl들을 담을 배열 선언.

        for (int i=0; i< deleteImages.length; i++) {
            imgUrls[i] = boardImgUrlsRepository.findById(deleteImages[i]).orElseThrow(() -> new IllegalArgumentException("해당 이미지는 없습니다."));
        }
        delete(imgUrls, "board");
    }
}