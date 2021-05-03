package com.sparta.hanghae.picturespot.dto.request.Comment;

import com.sparta.hanghae.picturespot.model.Board;
import com.sparta.hanghae.picturespot.model.Comment;
import com.sparta.hanghae.picturespot.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class CommentUpdateRequestDto {
    private String content;
    private User user;
    private Board board;

    public CommentUpdateRequestDto(String content, User user, Board board) {
        this.content = content;
        this.user =  user;
        this.board = board;
    }

    public Comment toEntity() {
        return Comment.builder()
                .content(content)
                .user(user)
                .board(board)
                .build();
    }
}
