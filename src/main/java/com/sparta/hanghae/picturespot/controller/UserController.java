package com.sparta.hanghae.picturespot.controller;

import com.sparta.hanghae.picturespot.dto.request.*;
import com.sparta.hanghae.picturespot.dto.response.*;
import com.sparta.hanghae.picturespot.model.User;
import com.sparta.hanghae.picturespot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입
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

    // 닉네임 중복체크
    @PostMapping("/user/signup/nickchk")
    public boolean nickchk(@RequestBody NicknameRequestDto nicknameRequestDto){
        boolean check = userService.nickchk(nicknameRequestDto.getNickname());
        if (check){
            return true; // 중복이 아니다.
        }
        else{
            return false; // 중복
        }
    }

    // 이메일 중복체크
    @PostMapping("/user/signup/emailchk")
    public boolean emailchk(@RequestBody EmailRequestDto emailRequestDto){
        boolean check = userService.emailchk(emailRequestDto.getEmail());
        if(check){
            return true; // 중복이 아니다.
        }else{
            return false; // 중복
        }
    }

    // 로그인
    @PostMapping("/user/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
        return userService.login(loginRequestDto);
    }

    // 이메일 찾기
    @PostMapping("/user/findemail")
    public EmailResponseDto findEmail(@RequestBody NicknameRequestDto nicknameRequestDto){
        return userService.findEmail(nicknameRequestDto.getNickname());
    }

    // 비밀번호 찾기
    @PostMapping("/user/findpwd")
    public AuthResponseDto findpwd(@RequestBody EmailRequestDto emailRequestDto){
       return userService.findpwd(emailRequestDto.getEmail());
    }

    // 비밀번호 수정
    @PostMapping("/user/editpwd")
    public ResponseEntity editpwd(@RequestBody PwEditRequestDto pwEditRequestDto){
        if(!pwEditRequestDto.getPassword().equals(pwEditRequestDto.getPwdchk())){
            MessageDto messageDto = new MessageDto("비밀번호가 같아야합니다.");
            return new ResponseEntity(messageDto,HttpStatus.INTERNAL_SERVER_ERROR);
        }else{
            userService.editpwd(pwEditRequestDto);
            MessageDto messageDto = new MessageDto("성공");
            return new ResponseEntity(messageDto, HttpStatus.OK);
        }


    }

    @PostMapping("/test")
    public ResponseEntity test(@AuthenticationPrincipal User user){
        MessageDto messageDto = new MessageDto(user.getEmail());
        return new ResponseEntity(messageDto, HttpStatus.OK);
    }
}
