package com.sparta.hanghae.picturespot.dto.request.mypage;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProfileRequestDto {

    private String imgUrl;
    private String introduceMsg;

    public ProfileRequestDto(String imgUrl, String introduceMsg){
        this.imgUrl = imgUrl;
        this.introduceMsg = introduceMsg;
    }

}
