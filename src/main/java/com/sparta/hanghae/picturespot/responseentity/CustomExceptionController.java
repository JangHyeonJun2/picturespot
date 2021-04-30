package com.sparta.hanghae.picturespot.responseentity;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class CustomExceptionController<T> {

    public ResponseEntity<Message> ok(String okMessage, T data) {
        HttpStatus status = HttpStatus.OK;
        Message messages = new Message(okMessage,data);
        return ResponseEntity
                .status(status)
                .body(messages);
    }
}
