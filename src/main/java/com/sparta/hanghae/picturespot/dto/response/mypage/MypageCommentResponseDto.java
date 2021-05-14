package com.sparta.hanghae.picturespot.dto.response.mypage;

import com.sparta.hanghae.picturespot.model.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class MypageCommentResponseDto {
    private Long boardId;
    private Long commentId;
    private String content;
    private Long writerId;
    private String writer;
    private String writerImgUrl;
    private LocalDateTime modified;

    public MypageCommentResponseDto(Comment comment){
        this.boardId = comment.getBoard().getId();
        this.commentId = comment.getId();
        this.writerId = comment.getUser().getId();
        this.writer = comment.getUser().getNickname();
        this.writerImgUrl = comment.getUser().getImgUrl();
        this.content = comment.getContent();
        this.modified = comment.getModified();
    }


}
