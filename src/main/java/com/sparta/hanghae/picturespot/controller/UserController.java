package com.sparta.hanghae.picturespot.controller;

import com.sparta.hanghae.picturespot.dto.SignupRequestDto;
import com.sparta.hanghae.picturespot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody SignupRequestDto requestDto)throws Exception{
        System.out.println("email "+requestDto.getEmail());
        System.out.println("nickname "+requestDto.getNickname());

        userService.signup(requestDto);
        return ResponseEntity.ok().build();
    }
}
