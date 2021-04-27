package com.sparta.hanghae.picturespot.controller;

import com.sparta.hanghae.picturespot.dto.requestDto.QCommentRequestDto;
import com.sparta.hanghae.picturespot.model.User;
import com.sparta.hanghae.picturespot.service.QCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class QCommentController {

    private final QCommentService qCommentService;

    //----User user는 jwt토큰에서 뽑아낸 user----//
    //----문의하기 댓글은 관리자만 작성할 수 있음----//

    //문의하기 댓글 쓰기
    @PostMapping("/qna/{qnaId}/qcomment")
    public ResponseEntity createQComment(@PathVariable Long qnaId, @RequestBody QCommentRequestDto qCommentRequestDto){
        return qCommentService.createQComment(qnaId, qCommentRequestDto);

    }

    //문의하기 댓글 수정
    @PutMapping("/qna/{qnaId}/qcomment/{qcommentId}")
    public ResponseEntity updateQComment(@PathVariable Long qnaId, @PathVariable Long qcommentId, @RequestBody QCommentRequestDto qCommentRequestDto){
        return qCommentService.updateQComment(qnaId, qcommentId, qCommentRequestDto);
    }

    //문의하기 댓글 삭제
    @DeleteMapping("/qna/{qnaId}/qcomment/{qcommentId}")
    public ResponseEntity deleteQComment(@PathVariable Long qnaId, @PathVariable Long qcommentId){
        return qCommentService.deleteQComment(qcommentId, qnaId);
    }
}
