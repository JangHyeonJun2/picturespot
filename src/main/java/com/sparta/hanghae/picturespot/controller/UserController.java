package com.sparta.hanghae.picturespot.controller;

import com.sparta.hanghae.picturespot.config.oauth2.exception.ResourceNotFoundException;
import com.sparta.hanghae.picturespot.config.oauth2.user.CurrentUser;
import com.sparta.hanghae.picturespot.dto.request.user.*;
import com.sparta.hanghae.picturespot.dto.response.user.AuthResponseDto;
import com.sparta.hanghae.picturespot.dto.response.user.EmailResponseDto;
import com.sparta.hanghae.picturespot.dto.response.user.LoginResponseDto;
import com.sparta.hanghae.picturespot.dto.response.user.MessageDto;
import com.sparta.hanghae.picturespot.model.EmailCheck;

import com.sparta.hanghae.picturespot.model.UserPrincipal;
import com.sparta.hanghae.picturespot.model.User;
import com.sparta.hanghae.picturespot.repository.EmailCheckRepository;
import com.sparta.hanghae.picturespot.repository.UserRepository;
import com.sparta.hanghae.picturespot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final EmailCheckRepository emailCheckRepository;
    private final UserRepository userRepository;

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
        // 닉네임 중복체크 && 이메일 중복체크
        //MessageDto messageDto = null;
        if(userService.nickchk(signupRequestDto.getNickname()) && (userService.emailchk(signupRequestDto.getEmail()))){ // 이거는 그냥 중복체크만 한거인데 인증을 한 상태인지 어캐 알지
            EmailCheck emailCheck = emailCheckRepository.findByEmail(signupRequestDto.getEmail());
            if(emailCheck==null){
                MessageDto messageDto = new MessageDto("이메일 인증을 해주세요");
                return new ResponseEntity(messageDto,HttpStatus.INTERNAL_SERVER_ERROR);
            }else{
                // 이메일 인증상태 확인
                if(emailCheck.getAuthCode().equals("Y")){
                    userService.signup(signupRequestDto);
                    MessageDto messageDto = new MessageDto("성공");
                    return new ResponseEntity(messageDto, HttpStatus.OK);
                }else{
                    MessageDto messageDto = new MessageDto("이메일 인증을 해주세요");
                    return new ResponseEntity(messageDto,HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
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

    // 이메일 중복체크 + 인증번호 발송 (회원가입된 이메일이 없을경우 인증번호를 발송하고 이미 회원가입이 되어있다면 null을 반환한다.)
    @PostMapping("/user/signup/emailchk")
    public boolean emailchkAuth(@RequestBody EmailRequestDto emailRequestDto){
        AuthResponseDto authResponseDto = userService.emailchkAuth(emailRequestDto.getEmail());
        if(authResponseDto == null){
            return false; // 이미 가입된 이메일이 있다.
        }else{
            return true; // 인증번호 이메일로 발송됨
        }

    }

    // 이메일 인증 확인
    @PostMapping("/user/signup/authcode")
    public boolean emailAuthCode(@RequestBody EmailAuthRequestDto emailAuthRequestDto){
        return userService.emailAuthCode(emailAuthRequestDto);
    }

    // 로그인
    @PostMapping("/user/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
        return userService.login(loginRequestDto);
    }

    // 이메일 찾기(nickname 필요)
    @PostMapping("/user/findemail")
    public EmailResponseDto findEmail(@RequestBody NicknameRequestDto nicknameRequestDto){
        return userService.findEmail(nicknameRequestDto.getNickname());
    }

    // 비밀번호 찾기 (가입된 이메일이 없다면 null을 반환한다.)
    @PostMapping("/user/findpwd")
    public boolean findpwd(@RequestBody EmailRequestDto emailRequestDto){
        AuthResponseDto authResponseDto = userService.findpwd(emailRequestDto.getEmail());
        if(authResponseDto == null){
            return false; // 가입안된 이메일
        }else{
            return true; // 인증번호 이메일로 발송됨
        }
    }

    // 비밀번호 코드 인증확인
    @PostMapping("/user/findpwd/authcode")
    public boolean pwdAuthCode(@RequestBody EmailAuthRequestDto emailAuthRequestDto){
        return userService.pwdAuthcode(emailAuthRequestDto);
    }

    // 비밀번호 수정
    @PostMapping("/user/findpwd/editpwd")
    public ResponseEntity editpwd(@RequestBody @Valid PwEditRequestDto pwEditRequestDto, Errors errors){
        if(errors.hasErrors()){
            Map<String, String> error = userService.validateHandling(errors);
            return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
        if(userService.nickchk(adminSignupRequestDto.getNickname()) && (userService.emailchk(adminSignupRequestDto.getEmail()))){
            userService.adminsignup(adminSignupRequestDto);
            MessageDto messageDto = new MessageDto("성공");
            return new ResponseEntity(messageDto, HttpStatus.OK);
        }else{
            MessageDto messageDto = new MessageDto("중복체크를 해주세요");
            return new ResponseEntity(messageDto,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // refresh토큰 재발급?
//    @PostMapping("/user/reissue")
//    public ResponseEntity<TokenDto> reissue(@RequestBody TokenDto tokenDto){
//        return ResponseEntity.ok(userService.reissue(tokenDto));
//    }



}