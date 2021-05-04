package com.sparta.hanghae.picturespot.dto.request.comment;

import com.sparta.hanghae.picturespot.model.Board;
import com.sparta.hanghae.picturespot.model.Comment;
import com.sparta.hanghae.picturespot.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentSaveRequestDto {
    private String content;
    private Board board;
    private User user;

    public CommentSaveRequestDto(String content) {
        this.content = content;
    }
    public void addBoardInComment(Board board) {
        this.board = board;
    }

    public void addUserInComment(User user) {
        this.user = user;
    }



    public Comment toEntity() {
        return Comment.builder()
                .content(content)
                .user(user)
                .board(board)
                .build();
    }
}
