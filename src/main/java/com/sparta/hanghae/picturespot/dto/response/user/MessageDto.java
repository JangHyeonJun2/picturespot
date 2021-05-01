package com.sparta.hanghae.picturespot.dto.response.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MessageDto {

    private String message;

    public MessageDto(String message){
        this.message = message;
    }
}
