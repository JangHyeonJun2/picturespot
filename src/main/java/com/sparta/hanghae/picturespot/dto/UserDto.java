package com.sparta.hanghae.picturespot.dto;

import com.sparta.hanghae.picturespot.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String nickname;
    private String email;
    private String password;
    private String introduceMsg;
    private String imgUrl;

    public UserDto(User user){
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.introduceMsg = user.getIntroduceMsg();
        this.imgUrl = user.getImgUrl();
    }
}
