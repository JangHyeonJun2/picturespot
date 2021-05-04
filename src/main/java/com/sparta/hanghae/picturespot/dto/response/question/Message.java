package com.sparta.hanghae.picturespot.dto.response.question;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Message {
    private String message;

    public Message(String message){
        this.message = message;
    }
}
