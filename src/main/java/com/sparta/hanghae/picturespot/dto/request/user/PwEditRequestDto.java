package com.sparta.hanghae.picturespot.dto.request.user;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class PwEditRequestDto {
    private String email;

    @NotBlank(message = "비밀번호를 비워둘 수 없습니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])((?=.*\\d)|(?=.*\\W)).{10,}$",
            message = "비밀번호 형식을 지켜주세요")
    private String password;

    @NotBlank(message = "비밀번호 체크를 비워둘 수 없습니다.")
    private String pwdchk;
}
