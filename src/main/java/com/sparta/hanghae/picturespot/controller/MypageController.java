//package com.sparta.hanghae.picturespot.controller;
//
//import com.sparta.hanghae.picturespot.dto.BoardDto;
//import com.sparta.hanghae.picturespot.dto.UserDto;
//import com.sparta.hanghae.picturespot.model.User;
//import com.sparta.hanghae.picturespot.service.MypageService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RequiredArgsConstructor
//@RestController
//public class MypageController {
//
//    private final MypageService mypageService;

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
//    @PutMapping("/editmyprofile")
//    public ResponseEntity editProfile(@RequestBody UserDto userDto, User user){
//        return mypageService.editProfile(user, userDto);
//    }
//
//}
