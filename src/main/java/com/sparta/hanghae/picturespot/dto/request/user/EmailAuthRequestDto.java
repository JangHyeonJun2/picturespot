package com.sparta.hanghae.picturespot.dto.request.user;

import lombok.Getter;

@Getter
public class EmailAuthRequestDto {
    private String email;
    private String authCode;
}
