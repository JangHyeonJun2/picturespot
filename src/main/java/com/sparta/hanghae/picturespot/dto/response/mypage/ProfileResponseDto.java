package com.sparta.hanghae.picturespot.dto.response.mypage;

import com.sparta.hanghae.picturespot.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProfileResponseDto {
    private Long userId;
    private String nickname;
    private String imgUrl;
    private String introduceMsg;

    public ProfileResponseDto(User editUser){
        this.userId = editUser.getId();
        this.nickname = editUser.getNickname();
        this.imgUrl = editUser.getImgUrl();
        this.introduceMsg = editUser.getIntroduceMsg();
    }
}
