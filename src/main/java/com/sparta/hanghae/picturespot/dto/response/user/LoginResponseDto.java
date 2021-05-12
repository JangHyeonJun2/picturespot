package com.sparta.hanghae.picturespot.dto.response.user;

import com.sparta.hanghae.picturespot.model.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponseDto {
    private String token;
    private String nickname;
    private String msg;
    private Long userId;
    private UserRole role;

    public LoginResponseDto(String token, String nickname, String msg, Long userId, UserRole role) {
        this.token = token;
        this.nickname = nickname;
        this.msg = msg;
        this.userId = userId;
        this.role = role;
    }
}
