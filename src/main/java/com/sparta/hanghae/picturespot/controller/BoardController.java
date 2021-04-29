//package com.sparta.hanghae.picturespot.controller;
//
//import com.sparta.hanghae.picturespot.dto.requestDto.BoardSaveRequestDto;
//import com.sparta.hanghae.picturespot.model.Board;
//import com.sparta.hanghae.picturespot.responseentity.CustomExceptionController;
//import com.sparta.hanghae.picturespot.responseentity.Message;
//import com.sparta.hanghae.picturespot.service.BoardService;
//import com.sparta.hanghae.picturespot.service.S3Service;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.util.List;
//
//@RequiredArgsConstructor
//@RestController
//public class BoardController {
//    private final BoardService boardService;
//    private final CustomExceptionController customExceptionController;
//    private final S3Service s3Service;
//
//    @PostMapping("/board")
//    public ResponseEntity save(@RequestParam(value = "file", required = false) MultipartFile files, @RequestParam("title") String title,
//                                        @RequestParam("content") String content, @RequestParam("category") String category, @RequestParam("latitude") BigDecimal latitude,
//                                        @RequestParam("longitude") BigDecimal longitude) {//todo 승욱님이 로그인 구현 다 하시면 detail받아서 사용자 찾기
//        String imgUrl = "";
//        try {
//            imgUrl = s3Service.upload(files, "board");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        BoardSaveRequestDto boardSaveRequestDto = new BoardSaveRequestDto(title,content,category,latitude,longitude,imgUrl);
//        Board save = boardService.save(boardSaveRequestDto);// 엔티티말고 dto로 보내기 (수장)
//        return customExceptionController.ok("게시물을 저장하였습니다.", save);
//    }
//}
