package com.sparta.hanghae.picturespot.dto;

import lombok.Getter;

@Getter
public class LoginResponseDto {
    private String token;
    private String email;
    private String msg;

    public LoginResponseDto(String token, String email, String msg) {
        this.token = token;
        this.email = email;
        this.msg = msg;
    }
}
