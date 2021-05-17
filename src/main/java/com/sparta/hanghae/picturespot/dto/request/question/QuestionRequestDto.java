package com.sparta.hanghae.picturespot.dto.request.question;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class QuestionRequestDto {
    private Long id;

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    private Long userId;

    private LocalDateTime created;
    private LocalDateTime modified;

    private List<QuestionRequestDto> questionRequestDtos;
    private List<QCommentRequestDto> qCommentRequestDtos;
}
