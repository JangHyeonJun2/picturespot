package com.sparta.hanghae.picturespot.dto.response.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EmailResponseDto {
    private String email;


    public EmailResponseDto(String email){
        this.email = email;
    }
}
