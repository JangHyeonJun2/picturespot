package com.sparta.hanghae.picturespot.dto.response.mypage;

import com.sparta.hanghae.picturespot.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NicknameResponseDto {
    private String nickname;

    public NicknameResponseDto(User editUser){
        this.nickname = editUser.getNickname();
    }
}
