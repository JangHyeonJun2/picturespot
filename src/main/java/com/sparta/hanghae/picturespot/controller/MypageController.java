package com.sparta.hanghae.picturespot.controller;

import com.sparta.hanghae.picturespot.dto.response.mypage.MypageResponseDto;
import com.sparta.hanghae.picturespot.model.User;
import com.sparta.hanghae.picturespot.responseentity.CustomExceptionController;
import com.sparta.hanghae.picturespot.service.MypageService;
import com.sparta.hanghae.picturespot.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MypageController {

    private final MypageService mypageService;
    private final CustomExceptionController customExceptionController;
    //private final S3Service s3Service;

    //---- GET요청 내 명소 사진, 내 명소 지도, 좋아요 사진, 좋아요 지도 boardRepo에서----//

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

    //프로필 편집 requestParam
//    @PutMapping("/editmyprofile")
//    public ResponseEntity editProfile(@RequestParam(value = "file") MultipartFile files, @RequestParam("nickname") String nickname, @RequestParam("introduceMsg") String introduceMsg, @AuthenticationPrincipal User user){
//        return mypageService.editProfile(files, nickname, introduceMsg, user);
//    }

}
