package com.sparta.hanghae.picturespot.dto.request.mypage;

import com.sparta.hanghae.picturespot.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProfileRequestDto {
    private String nickname;
    private String imgUrl;
    private String introduceMsg;

    public ProfileRequestDto(String imgUrl, String nickname, String introduceMsg){
        this.imgUrl = imgUrl;
        this.nickname = nickname;
        this.introduceMsg = introduceMsg;
    }

}
