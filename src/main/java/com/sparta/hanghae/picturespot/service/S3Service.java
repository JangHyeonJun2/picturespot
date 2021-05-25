package com.sparta.hanghae.picturespot.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
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
    private AmazonS3 amazonS3;
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
    //board
    public List<String> boardUpload(List<MultipartFile> multipartFile, String dirName) throws IOException {
        String[] result = changeUploadFileName(multipartFile, dirName);
        return Arrays.asList(result);
//        return changeUploadFileName(multipartFile, dirName);
    }
    //profile
    public String profileUpload(MultipartFile file, String dirName) throws IOException {
        return changeProfileFileName(file, dirName);
    }

    private String[] changeUploadFileName(List<MultipartFile> uploadFile, String dirName) throws IOException {
        List<String> uploadImgUrl = new ArrayList<>();

        for (int i = 0; i < uploadFile.size(); i++) {
            String replace = uploadFile.get(i).getOriginalFilename().replace(" ", ""); //공백 다 없애기
            log.info("changeFileName1: " + uploadFile.get(i).getOriginalFilename());
            String fileName = replace.substring(uploadFile.get(i).getOriginalFilename().lastIndexOf('.')); //.png 즉, 확장자와 . 앞에 문자 다 없애기
//            fileName = fileName.substring(0,fileName.lastIndexOf('.')+1); //todo 다시 테스트하기
            log.info("=======새로운 fileName : " + fileName);
            log.info("changeFileName2: " + fileName);
            Date date_now = new Date(System.currentTimeMillis()); // 현재시간을 가져와 Date형으로 저장한다

            //파일 이름을 다르게 한다. 날짜로만헀는데 for문이 너무 빠르게 돌아서 mmss까지 커버가 안되서 교체!
            UUID uuid = UUID.randomUUID();
            String subUUID = uuid.toString().substring(0, 8); //16자리로 생성되는데 너무 길어서 8자리로 짜름!
            SimpleDateFormat fourteen_format = new SimpleDateFormat("yyyyMMddHHmmss");
            String dateUuidFileName = subUUID + fourteen_format.format(date_now) + fileName;
            String resultFileName = dirName + "/" + dateUuidFileName;
            log.info("파일 이름 나타내기 2번째 : " + uploadFile.get(i).getName() + " ," + resultFileName);
            uploadImgUrl.add(putS3Aws(uploadFile.get(i), resultFileName));
        }
        return uploadImgUrl.toArray(new String[uploadImgUrl.size()]);
    }


    private String changeProfileFileName(MultipartFile uploadFile, String dirName) throws IOException {

        String replace = uploadFile.getOriginalFilename().replace(" ", ""); //공백 다 없애기
        log.info("changeFileName1: " + uploadFile.getOriginalFilename());
        String fileName = replace.substring(uploadFile.getOriginalFilename().lastIndexOf('.')); //.png 즉, 확장자와 . 앞에 문자 다 없애기
//            fileName = fileName.substring(0,fileName.lastIndexOf('.')+1); //todo 다시 테스트하기
        log.info("=======새로운 fileName : " + fileName);
        log.info("changeFileName2: " + fileName);
        Date date_now = new Date(System.currentTimeMillis()); // 현재시간을 가져와 Date형으로 저장한다

        //파일 이름을 다르게 한다. 날짜로만헀는데 for문이 너무 빠르게 돌아서 mmss까지 커버가 안되서 교체!
        UUID uuid = UUID.randomUUID();
        String subUUID = uuid.toString().substring(0, 8); //16자리로 생성되는데 너무 길어서 8자리로 짜름!
        SimpleDateFormat fourteen_format = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateUuidFileName = subUUID + fourteen_format.format(date_now) + fileName;
        String resultFileName = dirName + "/" + dateUuidFileName;
        log.info("파일 이름 나타내기 2번째 : " + uploadFile.getName() + " ," + resultFileName);
        String uploadImgUrl = putS3Aws(uploadFile, resultFileName);

        return uploadImgUrl;
    }

    private String putS3Aws(MultipartFile uploadFile, String fileName) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
//        log.info("파일 이름 나타내기 3번째 : " + uploadFile.toString().substring(uploadFile.toString().lastIndexOf("/")+1));
//        String substring = uploadFile.toString().substring(uploadFile.toString().lastIndexOf("/") + 1);
//        Process exec = Runtime.getRuntime().exec("find /home/ec2-user/app/ -name " + substring);
//        metadata.setContentType(uploadFile.getContentType());
//        metadata.setContentLength(uploadFile.getSize());
//        metadata.setHeader("filename",uploadFile.getOriginalFilename());
        log.info("파일 이름 나타내기 4번째 : " + uploadFile.getOriginalFilename());
//        File newFile= new File(uploadFile.toString().substring(uploadFile.toString().lastIndexOf("/")+1)).getAbsoluteFile();
//        amazonS3.putObject(new PutObjectRequest(bucket, fileName, uploadFile,metadata).withCannedAcl(CannedAccessControlList.PublicRead));
        amazonS3.putObject(new PutObjectRequest(bucket, fileName, uploadFile.getInputStream(), metadata).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    //bucket에 있는 imgUrl 삭제하는 메서드.
    public void delete(BoardImgUrls[] boardImgUrls, String dirName) throws IOException {
        String[] imgUrls = new String[boardImgUrls.length];
        for (int i = 0; i < boardImgUrls.length; i++) {
            imgUrls[i] = boardImgUrls[i].getImgUrl().substring(boardImgUrls[i].getImgUrl().lastIndexOf("/") + 1);
        }

        for (String imgUrl : imgUrls) {
            String objkeyArr = dirName + "/" + imgUrl;
            DeleteObjectsRequest delObjReq = new DeleteObjectsRequest(bucket).withKeys(objkeyArr);
            amazonS3.deleteObjects(delObjReq);
        }
    }

    //삭제할 이미지를 id로 찾고 delete 메서드 호출
    public void findImgUrls(List<Long> deleteImages) throws IOException {
        BoardImgUrls[] imgUrls = new BoardImgUrls[deleteImages.size()]; //id로 찾은 boardImgUrl들을 담을 배열 선언.

        for (int i = 0; i < deleteImages.size(); i++) {
            imgUrls[i] = boardImgUrlsRepository.findById(deleteImages.get(i)).orElseThrow(() -> new IllegalArgumentException("해당 이미지는 없습니다."));
        }
        delete(imgUrls, "board");
    }

    //profile
//    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
//        File uploadFile = convert(multipartFile)
//                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));
//
//        return upload(uploadFile, dirName);
//    }

    //profile
//    private String upload(File uploadFile, String dirName) throws IOException {
//
//        String fileName = uploadFile.getName().substring(uploadFile.getName().lastIndexOf('.'));
//
//
//        Date date_now = new Date(System.currentTimeMillis()); // 현재시간을 가져와 Date형으로 저장한다
//        SimpleDateFormat fourteen_format = new SimpleDateFormat("yyyyMMddHHmmss");
//
//        String dateFileName = fourteen_format.format(date_now) + fileName;
//        String resultFileName = dirName + "/" + dateFileName;
//        String uploadImgUrl = putS3(uploadFile,resultFileName);
//        removeNewFile(uploadFile);
//
//        return uploadImgUrl;
//    }

    //profile
//    private Optional<File> convert(MultipartFile file) throws IOException {
//        File convertFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
//        if (!convertFile.createNewFile()) {
//                return Optional.empty();
//        }else {
//            try(FileOutputStream fos = new FileOutputStream(convertFile)) {
//                fos.write(file.getBytes());
//            }
//        }
//        return Optional.of(convertFile);
//    }

    //    public String[] upload(List<MultipartFile> multipartFile, String dirName) throws IOException {
//        //System.out.println("확인!!");
//        File[] uploadFile = convert(multipartFile)
//                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));
//        log.info("파일 이름 나타내기 : "+uploadFile[0].getName());
//        return upload(uploadFile, dirName);
//    }
//
//    private String[] upload(File[] uploadFile, String dirName) throws IOException {
//        String[] uploadImgUrl = new String[uploadFile.length];
//
//
//        for (int i=0; i<uploadFile.length; i++) {
////            String fileName = uploadFile[i].getName().replace(" ", "");
//            String fileName = uploadFile[i].getName().substring(uploadFile[i].getName().lastIndexOf('.'));
//            Date date_now = new Date(System.currentTimeMillis()); // 현재시간을 가져와 Date형으로 저장한다
//            //파일 이름을 다르게 한다. 날짜로만헀는데 for문이 너무 빠르게 돌아서 mmss까지 커버가 안되서 교체!
//            UUID uuid = UUID.randomUUID();
//            String subUUID = uuid.toString().substring(0, 8);
//            SimpleDateFormat fourteen_format = new SimpleDateFormat("yyyyMMddHHmmss");
//
//            String dateFileName = fourteen_format.format(date_now) + fileName;
//            String resultFileName = dirName + "/" + subUUID +dateFileName;
//            log.info("파일 이름 나타내기 2번째 : "+ uploadFile[0].getName()+" ," + resultFileName);
////            uploadImgUrl[i] = putS3(uploadFile[i],resultFileName,i);
//            removeNewFile(uploadFile[i]);
//        }
//        return uploadImgUrl;
//    }
//
//
//    private Optional<File[]> convert(List<MultipartFile> file) throws IOException {
//        multiparts = new MultipartFile[file.size()];
//        File[] convertFiles = new File[file.size()];
//        for (int i=0; i<file.size(); i++) {
//            multiparts[i] = file.get(i);
//            convertFiles[i] = new File((file.get(i).getOriginalFilename()));
//            if (!convertFiles[i].exists()) {
//                log.info("파일이 존재하는지? : " + convertFiles[i].exists()+" ,"+convertFiles[i]);
//                convertFiles[i].mkdirs();
//                Runtime.getRuntime().exec("chmod 777 " + file.get(i).getOriginalFilename());
//                convertFiles[i].setExecutable(true, false);
//                convertFiles[i].setReadable(true, false);
//                convertFiles[i].setWritable(true, false);
//                if (!convertFiles[i].createNewFile()) {
//                    log.debug("===========Optional.emty 가 나옴!!===========");
//                    return Optional.empty();
//                }else {
//                    try(FileOutputStream fos = new FileOutputStream(convertFiles[i])) {
//                        fos.write(file.get(i).getBytes());
//                    }
//                }
//            }
//        }
//        return Optional.of(convertFiles);
//    }
//
    private String putS3(File uploadFile, String fileName) {
        amazonS3.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));

        return amazonS3.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) throws IOException {
        Runtime.getRuntime().exec("rm -r " + targetFile);
        log.info("파일이 삭제 되었습니다.");
//        if (targetFile.delete()) {
//            log.info("파일이 삭제되었습니다.");
//        } else {
//            log.info("파일이 삭제되지 못했습니다.");
//        }
    }

}