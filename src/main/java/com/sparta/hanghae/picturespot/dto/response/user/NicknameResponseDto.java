package com.sparta.hanghae.picturespot.dto.response.user;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NicknameResponseDto {
    private String nickname;

    public NicknameResponseDto(String nickname){
        this.nickname = nickname;
    }
}
