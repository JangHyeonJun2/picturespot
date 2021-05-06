package com.sparta.hanghae.picturespot.dto.response.question;

import com.sparta.hanghae.picturespot.model.QComment;
import com.sparta.hanghae.picturespot.model.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class QCommentResponseDto {
    private Long id;
    private String content;
    private UserRole writer;
    private Long questionId;

    private LocalDateTime modified;

    public QCommentResponseDto(QComment qComment){
        this.id = qComment.getId();
        this.content = qComment.getContent();
        this.writer = qComment.getUser().getRole();
        this.questionId = qComment.getQuestion().getId();
        this.modified = qComment.getModified();
    }
}
