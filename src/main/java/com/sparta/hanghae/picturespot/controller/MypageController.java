package com.sparta.hanghae.picturespot.controller;

import com.sparta.hanghae.picturespot.dto.request.mypage.ProfileRequestDto;
import com.sparta.hanghae.picturespot.dto.response.mypage.MypageResponseDto;
import com.sparta.hanghae.picturespot.dto.response.mypage.ProfileResponseDto;
import com.sparta.hanghae.picturespot.model.User;
import com.sparta.hanghae.picturespot.responseentity.CustomExceptionController;
import com.sparta.hanghae.picturespot.service.MypageService;
import com.sparta.hanghae.picturespot.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class MypageController {

    private final MypageService mypageService;
    private final CustomExceptionController customExceptionController;
    private final S3Service s3Service;


    //내 명소 + user정보
    @GetMapping("/mypage/myboard")
    public ResponseEntity getMyboard(@AuthenticationPrincipal User user){
        List<MypageResponseDto> myBoards = mypageService.getMyboard(user);
        return customExceptionController.ok("내가 쓴 게시물", myBoards);
    }

    //찜 명소 + user정보
    @GetMapping("/mypage/likeboard")
    public ResponseEntity getMylikeboard(@AuthenticationPrincipal User user){
        List<MypageResponseDto> likeBoards = mypageService.getMylikeboard(user);
        return customExceptionController.ok("내가 찜한 게시물", likeBoards);
    }

    //프로필 편집
    @PutMapping("/editmyprofile")
    public ResponseEntity editProfile(@RequestParam(value = "file", required = false) MultipartFile file, @RequestParam(value = "nickname", required = false) String nickname, @RequestParam(value = "introduceMsg", required = false) String introduceMsg, @AuthenticationPrincipal User user) throws IOException {
        String imgUrl = s3Service.upload(file, "profile");
        ProfileRequestDto profileDto = new ProfileRequestDto(imgUrl, nickname, introduceMsg);
        ProfileResponseDto myProfile = mypageService.editProfile(profileDto, user);
        return customExceptionController.ok("내 프로필 정보", myProfile);
    }

}
