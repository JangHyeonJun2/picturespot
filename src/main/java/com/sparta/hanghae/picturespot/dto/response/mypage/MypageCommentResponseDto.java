package com.sparta.hanghae.picturespot.dto.response.mypage;

import com.sparta.hanghae.picturespot.model.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MypageCommentResponseDto {
    private Long boardId;
    private Long commentId;
    private String content;
    private String writer;
    private LocalDateTime modified;

    public MypageCommentResponseDto(Comment comment){
        this.boardId = comment.getBoard().getId();
        this.commentId = comment.getId();
        this.writer = comment.getUser().getNickname();
        this.content = comment.getContent();
        this.modified = comment.getModified();
    }

}
