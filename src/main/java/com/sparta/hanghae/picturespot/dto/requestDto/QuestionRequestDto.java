package com.sparta.hanghae.picturespot.dto.requestDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class QuestionRequestDto {
    private Long id;
    private String title;
    private String content;
    private Long userId;

    private LocalDateTime created;
    private LocalDateTime modified;

    private List<QuestionRequestDto> questionRequestDtos;
    private List<QCommentRequestDto> qCommentRequestDtos;
}
