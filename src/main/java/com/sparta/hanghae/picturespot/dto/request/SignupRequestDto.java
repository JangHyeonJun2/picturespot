package com.sparta.hanghae.picturespot.dto.request;

import lombok.Getter;
import org.springframework.security.core.parameters.P;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class SignupRequestDto {
    @NotBlank(message = "닉네임을 비워둘 수 없습니다.")
    @Pattern(regexp = "^(?!(?:[0-9]+)$)([a-zA-Z]|[0-9a-zA-Z]){6,}$",
            message = "닉네임은 6자 이상의 영문 혹은 영문과 숫자를 조합")
    private String nickname;

    @NotBlank(message = "이메일을 비워둘 수 없습니다.")
    @Pattern(regexp = "^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$",
            message = "이메일 형식을 지켜주세요")
    private String email;

    @NotBlank(message = "비밀번호를 비워둘 수 없습니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])((?=.*\\d)|(?=.*\\W)).{10,}$",
            message = "비밀번호 형식을 지켜주세요")
    private String password;

    @NotBlank(message = "비밀번호 체크를 비워둘 수 없습니다.")
    //@Pattern(regexp = "(\\w)\\1\\1",
    //        message = "동일하게 입력해주세요")
    private String pwdchk;

    public SignupRequestDto(String nickname, String email, String password, String pwdchk){
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.pwdchk = pwdchk;
    }

}
