package com.sparta.hanghae.picturespot.controller;

import com.sparta.hanghae.picturespot.dto.request.user.*;
import com.sparta.hanghae.picturespot.dto.response.user.AuthResponseDto;
import com.sparta.hanghae.picturespot.dto.response.user.EmailResponseDto;
import com.sparta.hanghae.picturespot.dto.response.user.LoginResponseDto;
import com.sparta.hanghae.picturespot.dto.response.user.MessageDto;
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
import java.util.Random;

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

    // 관리자 회원가입
    @PostMapping("/user/admin/signup")
    public ResponseEntity adminsignup(@RequestBody @Valid AdminSignupRequestDto adminSignupRequestDto, Errors errors)throws Exception{
        if(errors.hasErrors()){
            Map<String, String> error = userService.validateHandling(errors);
            return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(!adminSignupRequestDto.getPassword().equals(adminSignupRequestDto.getPwdchk())){
            MessageDto messageDto = new MessageDto("비밀번호가 같아야합니다.");
            return new ResponseEntity(messageDto,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(userService.nickchk(adminSignupRequestDto.getNickname()) && userService.emailchk(adminSignupRequestDto.getEmail())){
            userService.adminsignup(adminSignupRequestDto);
            MessageDto messageDto = new MessageDto("성공");
            return new ResponseEntity(messageDto, HttpStatus.OK);
        }else{
            MessageDto messageDto = new MessageDto("중복체크를 해주세요");
            return new ResponseEntity(messageDto,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //@Secured("ROLE_ADMIN") secured 어노테이션을 사용할 때는 userDetails에서 ROLE_을 붙인 권한을 사용해줘야하는데 security 설정에서는 그냥 데이터베이스 컬럼에 들어가 있는 대로 검사하면 되는것 같다.
    @PostMapping("/test")
    public ResponseEntity test(@AuthenticationPrincipal User user){
        MessageDto messageDto = new MessageDto(user.getEmail());
        return new ResponseEntity(messageDto, HttpStatus.OK);
    }



}
