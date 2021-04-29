//package com.sparta.hanghae.picturespot.controller;
//
//import com.sparta.hanghae.picturespot.dto.BoardDto;
//import com.sparta.hanghae.picturespot.model.User;
//import com.sparta.hanghae.picturespot.service.MypageService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.List;
//
//@RequiredArgsConstructor
//@RestController
//public class MypageController {
//
//    private final MypageService mypageService;
//
//    //---- GET요청 내 명소 사진, 내 명소 지도, 좋아요 사진, 좋아요 지도 boardRepo에서----//
//
//    //내 명소 + user정보
//    @GetMapping("/mypage/myboard")
//    public List<BoardDto> getMyboard(User user){
//        return mypageService.getMyboard(user);
//    }
//
//    //찜 명소 + user정보
//    @GetMapping("/mypage/likeboard")
//    public List<BoardDto> getMylikeboard(User user){
//        return mypageService.getMylikeboard(user);
//    }
//
//    //프로필 편집 requestParam
////    @PutMapping("/editmyprofile")
////    public ResponseEntity editProfile(@RequestParam(value = "file") MultipartFile files, @RequestParam("introduceMsg") String introduceMsg, @AuthenticationPrincipal User user){
////        return mypageService.editProfile(user, userDto);
////    }
//
//}
