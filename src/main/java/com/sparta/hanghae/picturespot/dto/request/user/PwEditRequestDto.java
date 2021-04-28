package com.sparta.hanghae.picturespot.dto.request.user;

import lombok.Getter;

@Getter
public class PwEditRequestDto {
    private String email;
    private String password;
    private String pwdchk;
}
