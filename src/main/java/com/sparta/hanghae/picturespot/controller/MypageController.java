package com.sparta.hanghae.picturespot.controller;

import com.sparta.hanghae.picturespot.dto.request.mypage.NicknameRequestDto;
import com.sparta.hanghae.picturespot.dto.request.mypage.PasswordRequestDto;
import com.sparta.hanghae.picturespot.dto.response.mypage.MypageResponseDto;
import com.sparta.hanghae.picturespot.dto.response.mypage.NicknameResponseDto;
import com.sparta.hanghae.picturespot.dto.response.mypage.ProfileResponseDto;
import com.sparta.hanghae.picturespot.model.User;
import com.sparta.hanghae.picturespot.responseentity.CustomExceptionController;
import com.sparta.hanghae.picturespot.service.MypageService;
import com.sparta.hanghae.picturespot.service.S3Service;
import com.sparta.hanghae.picturespot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class MypageController {

    private final MypageService mypageService;
    private final CustomExceptionController customExceptionController;
    private final S3Service s3Service;
    private final UserService userService;


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
        return customExceptionController.ok("내가 좋아요 한 게시물", likeBoards);
    }

    //프로필 편집(사진, 자기소개)
    @PutMapping("/editmyprofile")
    public ResponseEntity editProfile(@RequestParam(value = "profileFile", required = false) MultipartFile file, @RequestParam(value = "introduceMsg", required = false) String introduceMsg, @AuthenticationPrincipal User user) throws IOException {
        if (!file.isEmpty()){
            String imgUrl = s3Service.upload(file, "profile");
            ProfileResponseDto myProfile = mypageService.editProfile(imgUrl, introduceMsg, user);
            return customExceptionController.ok("내 프로필 정보", myProfile);
        }else{
            String imgUrl = "";
            ProfileResponseDto myProfile = mypageService.editProfile(imgUrl, introduceMsg, user);
            return customExceptionController.ok("내 프로필 정보", myProfile);
        }
    }

    //프로필 닉네임 변경
    @PutMapping("/editnickname")
    public ResponseEntity editNick(@RequestBody NicknameRequestDto nickRequestDto, @AuthenticationPrincipal User user){
        NicknameResponseDto newNickname = mypageService.editNick(nickRequestDto, user);
        return customExceptionController.ok("새 닉네임", newNickname);
    }

    //비밀번호 변경
    @PutMapping("/editpwd")
    public ResponseEntity editPwd(@RequestBody @Valid PasswordRequestDto pwdRequestDto, Errors errors, @AuthenticationPrincipal User user){
        if(errors.hasErrors()){
            Map<String, String> error = userService.validateHandling(errors);
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }else{
            return mypageService.editPwd(pwdRequestDto, user);
        }
    }

    //다른 사람 페이지(업로드 한 게시물)
    @GetMapping("/story/{nickname}")
    public ResponseEntity getNickStory(@PathVariable String nickname){
        List<MypageResponseDto> nickStory = mypageService.getStory(nickname);
        return customExceptionController.ok("다른 유저가 올린 게시물", nickStory);
    }

    //다른 사람 페이지(좋아요 한 게시물)
    @GetMapping("/story/{nickname}/like")
    public ResponseEntity getNickLike(@PathVariable String nickname){
        List<MypageResponseDto> nickLikes = mypageService.getNickLike(nickname);
        return customExceptionController.ok("다른 유저가 좋아요 한 게시물", nickLikes);
    }


}
