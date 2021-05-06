package com.sparta.hanghae.picturespot.dto.request.mypage;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProfileRequestDto {
    //private Long id;
    //private String nickname;
    private String imgUrl;
    private String introduceMsg;

    public ProfileRequestDto(String imgUrl, String introduceMsg){
        //this.id = id;
        this.imgUrl = imgUrl;
        //this.nickname = nickname;
        this.introduceMsg = introduceMsg;
    }

}
