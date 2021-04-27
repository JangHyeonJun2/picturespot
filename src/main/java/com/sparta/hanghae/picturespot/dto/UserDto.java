package com.sparta.hanghae.picturespot.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDto {
    //private Long id;
    private String nickname;
    private String email;
    private String password;
    private String introduceMsg;
    private String imgUrl;
}
