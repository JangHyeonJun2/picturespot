package com.sparta.hanghae.picturespot.dto.request.question;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class QCommentRequestDto {
    private Long id;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;
}
