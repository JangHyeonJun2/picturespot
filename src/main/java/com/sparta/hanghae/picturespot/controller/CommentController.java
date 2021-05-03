package com.sparta.hanghae.picturespot.controller;

import com.sparta.hanghae.picturespot.dto.request.Comment.CommentSaveRequestDto;
import com.sparta.hanghae.picturespot.dto.request.Comment.CommentUpdateRequestDto;
import com.sparta.hanghae.picturespot.dto.response.comment.CommentSaveResponseDto;
import com.sparta.hanghae.picturespot.dto.response.comment.CommentUpdateResponseDto;
import com.sparta.hanghae.picturespot.model.User;
import com.sparta.hanghae.picturespot.responseentity.CustomExceptionController;
import com.sparta.hanghae.picturespot.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentService commentService;
    private final CustomExceptionController customExceptionController;

    //댓글 저장
    @PostMapping("/board/{boardId}/comment")
    public ResponseEntity addComment(@PathVariable Long boardId, @AuthenticationPrincipal User user, @RequestBody CommentSaveRequestDto requestDto) {
        CommentSaveResponseDto commentSaveResponseDto = commentService.addComment(boardId, user, requestDto);


        if (commentSaveResponseDto == null) {
            return customExceptionController.error("댓글이 비어있습니다.");
        }else
            return customExceptionController.ok("댓글이 저장되어 있습니다.", commentSaveResponseDto);
    }

    //댓글 수정
    @PutMapping("/board/comment/{commentId}")
    public ResponseEntity updateComment(@PathVariable Long commentId, @RequestBody CommentUpdateRequestDto requestDto, @AuthenticationPrincipal User user) {
        CommentUpdateResponseDto responseDto = commentService.updateComment(commentId, requestDto, user);
        if (responseDto != null) {
            return customExceptionController.ok("댓글이 수정되었습니다.", responseDto);
        } else
            return customExceptionController.error("사용자의 댓글이 아닙니다.");
    }

//    댓글 삭제
    @DeleteMapping("/board/comment/{commentId}")
    public ResponseEntity deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal User user) {
        commentService.deleteComment(commentId, user);
        return customExceptionController.ok("댓글이 삭제되었습니다..",commentId);
    }
}
