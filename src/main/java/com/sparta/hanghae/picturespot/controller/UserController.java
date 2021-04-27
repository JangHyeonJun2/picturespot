package com.sparta.hanghae.picturespot.controller;

import com.sparta.hanghae.picturespot.dto.request.LoginRequestDto;
import com.sparta.hanghae.picturespot.dto.response.EmailResponseDto;
import com.sparta.hanghae.picturespot.dto.response.LoginResponseDto;
import com.sparta.hanghae.picturespot.dto.request.SignupRequestDto;
import com.sparta.hanghae.picturespot.dto.response.MessageDto;
import com.sparta.hanghae.picturespot.dto.response.NicknameResponseDto;
import com.sparta.hanghae.picturespot.model.User;
import com.sparta.hanghae.picturespot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/user/signup")
    public ResponseEntity signup(@RequestBody @Valid SignupRequestDto signupRequestDto, Errors errors)throws Exception{
        if(errors.hasErrors()){
            Map<String, String> error = userService.validateHandling(errors);
            return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(!signupRequestDto.getPassword().equals(signupRequestDto.getPwdchk())){
            MessageDto messageDto = new MessageDto("비밀번호가 같아야합니다.");
            return new ResponseEntity(messageDto,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(userService.nickchk(signupRequestDto.getNickname()) && userService.emailchk(signupRequestDto.getEmail())){
            userService.signup(signupRequestDto);
            MessageDto messageDto = new MessageDto("성공");
            return new ResponseEntity(messageDto, HttpStatus.OK);
        }else{
            MessageDto messageDto = new MessageDto("중복체크를 해주세요");
            return new ResponseEntity(messageDto,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/user/signup/nickchk")
    public ResponseEntity nickchk(@RequestParam String nickname){
        boolean check = userService.nickchk(nickname);
        if (check){
            MessageDto messageDto = new MessageDto("성공");
            return new ResponseEntity(messageDto, HttpStatus.OK);
        }
        else{
            MessageDto messageDto = new MessageDto("닉네임 중복입니다.");
            return new ResponseEntity(messageDto,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/user/signup/emailchk")
    public ResponseEntity emailchk(@RequestParam String email){
        boolean check = userService.emailchk(email);
        if(check){
            MessageDto messageDto = new MessageDto("성공");
            return new ResponseEntity(messageDto, HttpStatus.OK);
        }else{
            MessageDto messageDto = new MessageDto("이메일 중복입니다.");
            return new ResponseEntity(messageDto,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/user/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
        return userService.login(loginRequestDto);
    }

    @PostMapping("/user/findemail")
    public EmailResponseDto findEmail(@RequestParam String nickname){
        return userService.findEmail(nickname);
    }

}
