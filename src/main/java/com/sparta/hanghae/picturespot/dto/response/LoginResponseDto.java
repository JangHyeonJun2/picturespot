package com.sparta.hanghae.picturespot.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponseDto {
    private String token;
    private String nickname;
    private String msg;

    public LoginResponseDto(String token, String nickname, String msg) {
        this.token = token;
        this.nickname = nickname;
        this.msg = msg;
    }
}
