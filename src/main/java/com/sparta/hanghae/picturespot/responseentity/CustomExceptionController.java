package com.sparta.hanghae.picturespot.responseentity;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class CustomExceptionController<T> {
    private HttpStatus status;
    public ResponseEntity<Message> ok(String okMessage, T data) {
        status = HttpStatus.OK;
        Message messages = new Message(okMessage,data);
        return ResponseEntity
                .status(status)
                .body(messages);
    }
    public ResponseEntity<Message> ok(String okMessage) {
        status = HttpStatus.OK;
        Message messages = new Message(okMessage);
        return ResponseEntity
                .status(status)
                .body(messages);
    }

    public ResponseEntity<Message> error(String errorMessage) {
        status = HttpStatus.BAD_REQUEST;
        Message message = new Message(errorMessage);
        return ResponseEntity
                .status(status)
                .body(message);
    }
}
