package com.sparta.hanghae.picturespot.dto.response.comment;

import com.sparta.hanghae.picturespot.model.Board;
import com.sparta.hanghae.picturespot.model.Comment;
import com.sparta.hanghae.picturespot.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentSaveResponseDto {
    private String title;
    private User user;
    private Board board;

    public CommentSaveResponseDto(Comment entity) {
        this.title = entity.getContent();
        this.user = entity.getUser();
        this.board = entity.getBoard();
    }
}


