package com.sparta.hanghae.picturespot.service;

import com.sparta.hanghae.picturespot.config.jwt.JwtTokenProvider;
import com.sparta.hanghae.picturespot.dto.request.user.*;
import com.sparta.hanghae.picturespot.dto.response.user.AuthResponseDto;
import com.sparta.hanghae.picturespot.dto.response.user.EmailResponseDto;
import com.sparta.hanghae.picturespot.dto.response.user.LoginResponseDto;
import com.sparta.hanghae.picturespot.model.EmailCheck;
import com.sparta.hanghae.picturespot.model.PwdCheck;
import com.sparta.hanghae.picturespot.model.User;
import com.sparta.hanghae.picturespot.model.UserRole;
import com.sparta.hanghae.picturespot.repository.EmailCheckRepository;
import com.sparta.hanghae.picturespot.repository.PwdCheckRepository;
import com.sparta.hanghae.picturespot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.beans.factory.annotation.Value;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EmailCheckRepository emailCheckRepository;
    private final PwdCheckRepository pwdCheckRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final ModelMapper modelMapper;
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    private static final String ADMIN_TOKEN = "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";

    public void signup(SignupRequestDto requestDto){

        String encodPassword = bCryptPasswordEncoder.encode(requestDto.getPassword());
        UserRole role = UserRole.USER;
        User user = new User(requestDto.getNickname(), requestDto.getEmail(), encodPassword, role);

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
        return new LoginResponseDto(jwtTokenProvider.createToken(user.getEmail()), user.getNickname(), "성공");
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

    // 단순 이메일 중복체크
    public boolean emailchk(String email){
        User user = userRepository.findByEmail(email);
        if(user == null){
            return true; // 중복이 아니다
        }else{
            return false; // 중복
        }
    }


    // 이메일 인증발송
    @Transactional
    public AuthResponseDto emailchkAuth(String email) {
        User user = userRepository.findByEmail(email);
        if(user == null){
            // 이메일 발송
            String auth = certified_key();
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setFrom(from);
            message.setSubject("이메일 인증");
            message.setText(auth);
            javaMailSender.send(message);

            // emailcheck 테이블 생성
            EmailCheck emailCheck = emailCheckRepository.findByEmail(email);
            if(emailCheck == null) {
                EmailCheck check = new EmailCheck(email, auth);
                emailCheckRepository.save(check);
            }else{
                emailCheck.update(auth);
            }
            return new AuthResponseDto(auth);
        }else {
            return null; // 중복
        }
    }

    // 이메일 인증번호 확인
    @Transactional
    public boolean emailAuthCode(EmailAuthRequestDto emailAuthRequestDto) {
        EmailCheck emailCheck = emailCheckRepository.findByEmail(emailAuthRequestDto.getEmail());
        if (emailCheck == null) {
            return false;
        }else{
            if(emailCheck.getAuthCode().equals(emailAuthRequestDto.getAuthCode())){
                emailCheck.update("Y");
                return true; // 이메일 인증 완료
            }
            return false;
        }
    }

    // 이메일 찾기
    public EmailResponseDto findEmail(String nickname) {
        User user = userRepository.findByNickname(nickname);
        if(user == null){
            return null;
        }else{
            return new EmailResponseDto(user.getEmail());
        }
    }

    // 비밀번호 찾기
    @Transactional
    public AuthResponseDto findpwd(String email) {
        User user = userRepository.findByEmail(email);
        if(user == null){
            return null;
        }else{
            // 이메일로 인증번호 발송
            String auth = certified_key();
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("비밀번호 찾기");
            message.setText(auth);
            javaMailSender.send(message);

            // EmailCheck 테이블에 authCode 저장
            PwdCheck pwdCheck = pwdCheckRepository.findByEmail(email);
            if(pwdCheck == null) {
                PwdCheck check = new PwdCheck(email, auth);
                pwdCheckRepository.save(check);
            }else{
                pwdCheck.update(auth);
            }
            return new AuthResponseDto(auth);
        }
    }

    // 비밀번호 인증코드 확인
    @Transactional
    public boolean pwdAuthcode(EmailAuthRequestDto emailAuthRequestDto) {
        PwdCheck pwdCheck = pwdCheckRepository.findByEmail(emailAuthRequestDto.getEmail());
        if (pwdCheck == null) {
            return false;
        }else{
            if(pwdCheck.getAuthCode().equals(emailAuthRequestDto.getAuthCode())){
                pwdCheck.update("Y");
                return true; // 이메일 인증 완료
            }
            return false;
        }
    }

    @Transactional
    // 비밀번호 수정
    public void editpwd(PwEditRequestDto pwEditRequestDto) {
        User user = userRepository.findByEmail(pwEditRequestDto.getEmail());
        if(user == null){
            throw new IllegalStateException("가입된 이메일이 없습니다.");
        }else{

            PwdCheck pwdCheck = pwdCheckRepository.findByEmail(pwEditRequestDto.getEmail());
            if(pwdCheck.getAuthCode().equals("Y")){
                String encodPassword = bCryptPasswordEncoder.encode(pwEditRequestDto.getPassword());
                user.updatePw(encodPassword);
            }

        }
    }

    // 인증번호 생성
    private String certified_key() {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        int num = 0;

        do {
            num = random.nextInt(75) + 48;
            if ((num >= 48 && num <= 57) || (num >= 65 && num <= 90) || (num >= 97 && num <= 122)) {
                sb.append((char) num);
            } else {
                continue;
            }

        } while (sb.length() < 10);
        return sb.toString();
    }

    // 관리자 회원가입
    public void adminsignup(AdminSignupRequestDto adminSignupRequestDto) {
        if (!adminSignupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
            throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
        }
        String encodPassword = bCryptPasswordEncoder.encode(adminSignupRequestDto.getPassword());
        UserRole role = UserRole.ADMIN;
        User user = new User(adminSignupRequestDto.getNickname(), adminSignupRequestDto.getEmail(), encodPassword, role);
        userRepository.save(user);
    }



}
