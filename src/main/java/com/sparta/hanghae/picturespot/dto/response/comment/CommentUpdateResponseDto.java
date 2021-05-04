package com.sparta.hanghae.picturespot.dto.response.comment;

import com.sparta.hanghae.picturespot.model.Board;
import com.sparta.hanghae.picturespot.model.Comment;
import com.sparta.hanghae.picturespot.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentUpdateResponseDto {
    private String content;
    private User user;
    private Board board;

    public CommentUpdateResponseDto(Comment entity) {
        this.content = entity.getContent();
        this.user = entity.getUser();
        this.board = entity.getBoard();
    }
}
