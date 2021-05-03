package com.sparta.hanghae.picturespot.controller;

import com.sparta.hanghae.picturespot.dto.request.Comment.CommentSaveRequestDto;
import com.sparta.hanghae.picturespot.dto.response.comment.CommentSaveResponseDto;
import com.sparta.hanghae.picturespot.model.Comment;
import com.sparta.hanghae.picturespot.model.User;
import com.sparta.hanghae.picturespot.responseentity.CustomExceptionController;
import com.sparta.hanghae.picturespot.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
