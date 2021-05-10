package com.sparta.hanghae.picturespot.controller;

import com.sparta.hanghae.picturespot.dto.request.mypage.NicknameRequestDto;
import com.sparta.hanghae.picturespot.dto.request.mypage.PasswordRequestDto;
import com.sparta.hanghae.picturespot.dto.response.mypage.MypageResponseDto;
import com.sparta.hanghae.picturespot.dto.response.mypage.NicknameResponseDto;
import com.sparta.hanghae.picturespot.dto.response.mypage.ProfileResponseDto;
import com.sparta.hanghae.picturespot.model.UserPrincipal;
import com.sparta.hanghae.picturespot.responseentity.CustomExceptionController;
import com.sparta.hanghae.picturespot.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


@RequiredArgsConstructor
@RestController
public class MypageController {

    private final MypageService mypageService;
    private final CustomExceptionController customExceptionController;


    //유저 프로필
    @GetMapping("/profile/{userId}")
    public ResponseEntity getMyprofile(@PathVariable Long userId){
        ProfileResponseDto profile = mypageService.getMyprofile(userId);
        return customExceptionController.ok("프로필 정보", profile);
    }

    //유저가 올린 게시물
    @GetMapping("/story/{userId}/board")
    public ResponseEntity getMyboard(@PathVariable Long userId, @AuthenticationPrincipal UserPrincipal user){
        List<MypageResponseDto> myBoards = mypageService.getMyboard(userId, user);
        return customExceptionController.ok("유저가 작성한 게시물", myBoards);
    }

    //유저가 좋아요 한 게시물
    @GetMapping("/story/{userId}/likeboard")
    public ResponseEntity getMylikeboard(@PathVariable Long userId, @AuthenticationPrincipal UserPrincipal user){
        List<MypageResponseDto> myBoards = mypageService.getMylikeboard(userId, user);
        return customExceptionController.ok("유저가 좋아요 한 게시물", myBoards);
    }

    //프로필 편집(사진, 자기소개)
    @PutMapping("/editmyprofile/{userId}")
    public ResponseEntity editProfile(@RequestParam(value = "profileFile", required = false) MultipartFile file, @RequestParam(value = "introduceMsg", required = false) String introduceMsg, @AuthenticationPrincipal UserPrincipal user, @PathVariable Long userId) throws IOException {
        ProfileResponseDto myProfile = mypageService.editProfile(user, file, introduceMsg, userId);
        return customExceptionController.ok("내 프로필 정보", myProfile);
    }

    //프로필 닉네임 변경
    @PutMapping("/editnickname/{userId}")
    public ResponseEntity editNick(@RequestBody NicknameRequestDto nickRequestDto, @AuthenticationPrincipal UserPrincipal user, @PathVariable Long userId){
        NicknameResponseDto newNickname = mypageService.editNick(nickRequestDto, user, userId);
        return customExceptionController.ok("새 닉네임", newNickname);
    }

    //비밀번호 변경
    @PutMapping("/editpwd/{userId}")
    public ResponseEntity editPwd(@RequestBody @Valid PasswordRequestDto pwdRequestDto, Errors errors, @AuthenticationPrincipal UserPrincipal user, @PathVariable Long userId) {
        return mypageService.editPwd(pwdRequestDto, errors, user, userId);
    }
}
