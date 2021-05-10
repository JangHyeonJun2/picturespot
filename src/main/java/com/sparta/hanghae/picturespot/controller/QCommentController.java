package com.sparta.hanghae.picturespot.controller;

import com.sparta.hanghae.picturespot.dto.request.question.QCommentRequestDto;
import com.sparta.hanghae.picturespot.model.User;
import com.sparta.hanghae.picturespot.model.UserPrincipal;
import com.sparta.hanghae.picturespot.repository.UserRepository;
import com.sparta.hanghae.picturespot.service.QCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class QCommentController {

    private final QCommentService qCommentService;
    //----문의하기 댓글은 관리자만 작성할 수 있음----//
    //----문의하기 댓글은 문의하기 게시물 상세 요청 시 함께 볼 수 있음---//

    //문의하기 댓글 쓰기
    @PostMapping("/qcomment/{qnaId}")
    public ResponseEntity createQComment(@PathVariable Long qnaId, @RequestBody QCommentRequestDto qCommentRequestDto, @AuthenticationPrincipal UserPrincipal user){
        return qCommentService.createQComment(qnaId, qCommentRequestDto, user);
    }

    //문의하기 댓글 수정
    @PutMapping("/qcomment/{qcommentId}/qna/{qnaId}")
    public ResponseEntity updateQComment(@PathVariable Long qnaId, @PathVariable Long qcommentId, @RequestBody QCommentRequestDto qCommentRequestDto, @AuthenticationPrincipal UserPrincipal user){
        return qCommentService.updateQComment(qnaId, qcommentId, qCommentRequestDto, user);
    }

    //문의하기 댓글 삭제
    @DeleteMapping("/qcomment/{qcommentId}/qna/{qnaId}")
    public ResponseEntity deleteQComment(@PathVariable Long qnaId, @PathVariable Long qcommentId, @AuthenticationPrincipal UserPrincipal user){
        return qCommentService.deleteQComment(qcommentId, qnaId, user);
    }
}
