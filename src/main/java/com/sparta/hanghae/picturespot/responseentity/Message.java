package com.sparta.hanghae.picturespot.responseentity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Message<T> {
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
//    private LocalDateTime dateTime;

    private String message;

    private T data;

    public Message(String msg) {
//        this.dateTime = LocalDateTime.now();
        this.message = msg;
    }

    public Message(String msg, T data) {
//        this.dateTime = LocalDateTime.now();
        this.message = msg;
        this.data = data;
    }
}
