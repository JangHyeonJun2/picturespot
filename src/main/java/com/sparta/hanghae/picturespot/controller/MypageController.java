package com.sparta.hanghae.picturespot.controller;

import com.sparta.hanghae.picturespot.dto.request.mypage.NicknameRequestDto;
import com.sparta.hanghae.picturespot.dto.request.mypage.PasswordRequestDto;
import com.sparta.hanghae.picturespot.dto.response.mypage.MypageResponseDto;
import com.sparta.hanghae.picturespot.dto.response.mypage.NicknameResponseDto;
import com.sparta.hanghae.picturespot.dto.response.mypage.ProfileResponseDto;
import com.sparta.hanghae.picturespot.model.User;
import com.sparta.hanghae.picturespot.model.UserPrincipal;
import com.sparta.hanghae.picturespot.repository.UserRepository;
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
    private final UserRepository userRepository;



    //유저 정보
    @GetMapping("/profile/{userId}")
    public ResponseEntity getMyprofile(@PathVariable Long userId){
        User findUser = userRepository.findById(userId).orElseThrow(
                ()->new IllegalArgumentException("해당 사용자가 없습니다."));
        ProfileResponseDto profile = mypageService.getMyprofile(findUser);
        return customExceptionController.ok("프로필 정보", profile);
    }

    //유저가 올린 게시물
    @GetMapping("/story/{userId}/board")
    public ResponseEntity getMyboard(@PathVariable Long userId, @AuthenticationPrincipal UserPrincipal user){
        User findUser = userRepository.findById(userId).orElseThrow(
                ()->new IllegalArgumentException("해당 사용자가 없습니다."));

        List<MypageResponseDto> myBoards = mypageService.getMyboard(findUser, user);
        return customExceptionController.ok("유저가 작성한 게시물", myBoards);
    }

    //유저가 좋아요 한 게시물
    @GetMapping("/story/{userId}/likeboard")
    public ResponseEntity getMylikeboard(@PathVariable Long userId, @AuthenticationPrincipal UserPrincipal user){
        User findUser = userRepository.findById(userId).orElseThrow(
                ()->new IllegalArgumentException("해당 사용자가 없습니다."));

        List<MypageResponseDto> myBoards = mypageService.getMylikeboard(findUser, user);
        return customExceptionController.ok("유저가 좋아요 한 게시물", myBoards);

    }

    //프로필 편집(사진, 자기소개)
    @PutMapping("/editmyprofile")
    public ResponseEntity editProfile(@RequestParam(value = "profileFile", required = false) MultipartFile file, @RequestParam(value = "introduceMsg", required = false) String introduceMsg, @AuthenticationPrincipal UserPrincipal user) throws IOException {
        User findUser = userRepository.findById(user.getId()).orElseThrow(
                ()->new IllegalArgumentException("해당 사용자가 없습니다."));

        if (!(file == null)){
            String imgUrl = s3Service.upload(file, "profile");
            ProfileResponseDto myProfile = mypageService.editProfile(imgUrl, introduceMsg, findUser);
            return customExceptionController.ok("내 프로필 정보", myProfile);
        }else{
            String imgUrl = findUser.getImgUrl();
            ProfileResponseDto myProfile = mypageService.editProfile(imgUrl, introduceMsg, findUser);
            return customExceptionController.ok("내 프로필 정보", myProfile);
        }
    }

    //프로필 닉네임 변경
    @PutMapping("/editnickname")
    public ResponseEntity editNick(@RequestBody NicknameRequestDto nickRequestDto, @AuthenticationPrincipal UserPrincipal user){
        User findUser = userRepository.findById(user.getId()).orElseThrow(
                ()->new IllegalArgumentException("해당 사용자가 없습니다."));
        NicknameResponseDto newNickname = mypageService.editNick(nickRequestDto, findUser);
        return customExceptionController.ok("새 닉네임", newNickname);
    }

    //비밀번호 변경
    @PutMapping("/editpwd")
    public ResponseEntity editPwd(@RequestBody @Valid PasswordRequestDto pwdRequestDto, Errors errors, @AuthenticationPrincipal UserPrincipal user){
        User findUser = userRepository.findById(user.getId()).orElseThrow(
                ()->new IllegalArgumentException("해당 사용자가 없습니다."));
        if(errors.hasErrors()){
            Map<String, String> error = userService.validateHandling(errors);
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }else{
            return mypageService.editPwd(pwdRequestDto, findUser);
        }
    }

}
