package com.sparta.hanghae.picturespot.dto.response;

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
