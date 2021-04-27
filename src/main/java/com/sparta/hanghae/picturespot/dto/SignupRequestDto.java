package com.sparta.hanghae.picturespot.dto;

import lombok.Getter;

@Getter
public class SignupRequestDto {
    private String nickname;
    private String email;
    private String password;
    private String pwdchk;

    public SignupRequestDto(String nickname, String email, String password, String pwdchk){
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.pwdchk = pwdchk;
    }

}
