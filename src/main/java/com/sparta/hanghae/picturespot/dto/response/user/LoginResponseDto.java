package com.sparta.hanghae.picturespot.dto.response.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponseDto {
    private String token;
    private String nickname;
    private String msg;
    private Long userId;

    public LoginResponseDto(String token, String nickname, String msg, Long userId) {
        this.token = token;
        this.nickname = nickname;
        this.msg = msg;
        this.userId = userId;
    }
}
