package com.sparta.hanghae.picturespot.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
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
