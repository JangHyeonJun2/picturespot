package com.sparta.hanghae.picturespot.service;

import com.sparta.hanghae.picturespot.config.jwt.JwtTokenProvider;
import com.sparta.hanghae.picturespot.dto.request.LoginRequestDto;
import com.sparta.hanghae.picturespot.dto.response.EmailResponseDto;
import com.sparta.hanghae.picturespot.dto.response.LoginResponseDto;
import com.sparta.hanghae.picturespot.dto.request.SignupRequestDto;
import com.sparta.hanghae.picturespot.dto.response.MessageDto;
import com.sparta.hanghae.picturespot.model.User;
import com.sparta.hanghae.picturespot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final ModelMapper modelMapper;

    public void signup(SignupRequestDto requestDto){
        String encodPassword = bCryptPasswordEncoder.encode(requestDto.getPassword());
        User user = new User(requestDto.getNickname(), requestDto.getEmail(), encodPassword);
        userRepository.save(user);
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail());
        if(user == null){
            throw new IllegalStateException("가입되지 않은 email 입니다.");
        }
        if(!bCryptPasswordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())){
            throw new IllegalStateException("잘못된 비밀번호 입니다.");
        }
        return new LoginResponseDto(jwtTokenProvider.createToken(user.getEmail()), user.getEmail(), "성공");
    }

    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();
        for(FieldError error : errors.getFieldErrors()){
            String validKeyName = error.getField();
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }

    public boolean nickchk(String nickname) {
        User user = userRepository.findByNickname(nickname);
        if(user == null){
            return true; // 중복이 아니다
        }else{
            return false; // 중복
        }
    }

    public boolean emailchk(String email) {
        User user = userRepository.findByEmail(email);
        if(user == null){
            return true; // 중복이 아니다.
        }else {
            return false; // 중복
        }
    }

    public EmailResponseDto findEmail(String nickname) {
        User user = userRepository.findByNickname(nickname);
        if(user == null){
            throw new IllegalStateException("nickname으로 가입된 이메일이 없습니다.");
        }else{
            return new EmailResponseDto(user.getEmail());
        }
    }
}
