package com.sparta.hanghae.picturespot.dto.response.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthResponseDto {
    private String auth;

    public AuthResponseDto(String auth){
        this.auth = auth;
    }

}
