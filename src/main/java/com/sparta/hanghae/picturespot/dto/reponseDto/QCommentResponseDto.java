package com.sparta.hanghae.picturespot.dto.reponseDto;

import com.sparta.hanghae.picturespot.model.QComment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class QCommentResponseDto {
    private Long id;
    private String content;
    private String writer;
    private Long questionId;

    private LocalDateTime modified;

    public QCommentResponseDto(QComment qComment){
        this.id = qComment.getId();
        this.content = qComment.getContent();
        //this.writer = qComment.getUser().getNickname();
        this.questionId = qComment.getQuestion().getId();
        this.modified = qComment.getModified();
    }
}
