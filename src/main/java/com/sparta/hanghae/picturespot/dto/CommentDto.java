package com.sparta.hanghae.picturespot.dto;

import com.sparta.hanghae.picturespot.model.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
public class CommentDto {
    private Long id;
    private String content;
    private String writer;
    private Long boardId;
    private LocalDateTime modified;

    public CommentDto(Comment comment){
        this.id = comment.getId();
        this.content = comment.getContent();
        this.writer = comment.getUser().getNickname();
        this.boardId = comment.getBoard().getId();
        this.modified = comment.getModified();
    }
}
