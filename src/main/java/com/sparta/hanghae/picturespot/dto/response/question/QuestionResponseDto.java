package com.sparta.hanghae.picturespot.dto.response.question;

import com.sparta.hanghae.picturespot.dto.response.question.QCommentResponseDto;
import com.sparta.hanghae.picturespot.model.Question;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class QuestionResponseDto {
    private Long id;
    private String title;
    private String content;
    private String writer;
    private long qnaSize;
    private int pageSize;

    private LocalDateTime modified;

    //private List<QuestionResponseDto> questions;
    private List<QCommentResponseDto> qcomments;

    public QuestionResponseDto(Question question) {
        this.id = question.getId();
        this.title = question.getTitle();
        this.content = question.getContent();
        this.writer = question.getUser().getNickname();
        this.modified = question.getModified();
    }

    public QuestionResponseDto(Question question, Long qnaSize, int pageSize) {
        this.id = question.getId();
        this.title = question.getTitle();
        this.content = question.getContent();
        this.writer = question.getUser().getNickname();
        this.modified = question.getModified();
        this.qnaSize = qnaSize;
        this.pageSize = pageSize;
    }

    public QuestionResponseDto(Question question, List<QCommentResponseDto> qCommentResponseDtos){
        this.id = question.getId();
        this.title = question.getTitle();
        this.content = question.getContent();
        this.writer = question.getUser().getNickname();
        this.modified = question.getModified();
        this.qcomments = qCommentResponseDtos;
    }



}
