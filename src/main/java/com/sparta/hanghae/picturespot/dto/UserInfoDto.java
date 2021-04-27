package com.sparta.hanghae.picturespot.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserInfoDto {
    private Long id;
    private String nickname;
    private String email;
    private String token;

    public UserInfoDto(Long id, String nickname, String email, String token){
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.token = token;
    }
}
