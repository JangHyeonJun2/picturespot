package com.sparta.hanghae.picturespot.controller;

import com.sparta.hanghae.picturespot.model.User;
import com.sparta.hanghae.picturespot.responseentity.CustomExceptionController;
import com.sparta.hanghae.picturespot.service.HeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HeartController {
    private final HeartService heartService;
    private final CustomExceptionController customExceptionController;

    @PostMapping("/board/{boardId}/like")
    public ResponseEntity addHeart(@PathVariable Long boardId, @AuthenticationPrincipal User user) {
        boolean checkResult = heartService.addHeart(boardId, user.getId());
        if (checkResult) {
            return customExceptionController.ok("좋아요를 하였습니다.", true);
        } else
            return  customExceptionController.error("이미 좋아요를 선택하였습니다.");
    }

    @DeleteMapping("/board/{boardId}/like")
    public ResponseEntity deleteHeart(@PathVariable Long boardId, @AuthenticationPrincipal User user) {
        boolean checkResult = heartService.deleteHeart(boardId, user.getId());

        if (checkResult) {
            return customExceptionController.ok("좋아요를 취소하였습니다.", true);
        } else
            return  customExceptionController.error("좋아요를 먼저 하셔야합니다.");
    }


}
