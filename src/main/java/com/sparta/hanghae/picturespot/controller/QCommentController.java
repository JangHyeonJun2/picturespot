package com.sparta.hanghae.picturespot.controller;

import com.sparta.hanghae.picturespot.dto.request.question.QCommentRequestDto;
import com.sparta.hanghae.picturespot.dto.response.question.QCommentResponseDto;
import com.sparta.hanghae.picturespot.model.User;
import com.sparta.hanghae.picturespot.model.UserPrincipal;
import com.sparta.hanghae.picturespot.repository.UserRepository;
import com.sparta.hanghae.picturespot.responseentity.CustomExceptionController;
import com.sparta.hanghae.picturespot.service.QCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class QCommentController {

    private final QCommentService qCommentService;
    private final CustomExceptionController customExceptionController;
    //----문의하기 댓글은 관리자만 작성할 수 있음----//
    //----문의하기 댓글은 문의하기 게시물 상세 요청 시 함께 볼 수 있음---//

    //문의하기 댓글 쓰기
    @PostMapping("/qcomment/{qnaId}")
    public ResponseEntity createQComment(@PathVariable Long qnaId, @RequestBody @Valid QCommentRequestDto qCommentRequestDto, Errors errors, @AuthenticationPrincipal UserPrincipal user){
        QCommentResponseDto qComment = qCommentService.createQComment(qnaId, qCommentRequestDto, errors, user);
        return customExceptionController.ok("댓글 등록 완료", qComment);
    }

    //문의하기 댓글 수정
    @PutMapping("/qcomment/{qcommentId}/qna/{qnaId}")
    public ResponseEntity updateQComment(@PathVariable Long qnaId, @PathVariable Long qcommentId, @RequestBody @Valid QCommentRequestDto qCommentRequestDto, Errors errors, @AuthenticationPrincipal UserPrincipal user){
        QCommentResponseDto qComment = qCommentService.updateQComment(qnaId, qcommentId, qCommentRequestDto, errors, user);
        return customExceptionController.ok("댓글 수정 완료", qComment);
    }

    //문의하기 댓글 삭제
    @DeleteMapping("/qcomment/{qcommentId}/qna/{qnaId}")
    public ResponseEntity deleteQComment(@PathVariable Long qnaId, @PathVariable Long qcommentId, @AuthenticationPrincipal UserPrincipal user){
        return qCommentService.deleteQComment(qcommentId, qnaId, user);
    }
}
