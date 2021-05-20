package com.sparta.hanghae.picturespot.service;

import com.sparta.hanghae.picturespot.config.jwt.JwtTokenProvider;
import com.sparta.hanghae.picturespot.dto.request.user.*;
import com.sparta.hanghae.picturespot.dto.response.user.AuthResponseDto;
import com.sparta.hanghae.picturespot.dto.response.user.EmailResponseDto;
import com.sparta.hanghae.picturespot.dto.response.user.LoginResponseDto;
import com.sparta.hanghae.picturespot.model.*;
import com.sparta.hanghae.picturespot.repository.EmailCheckRepository;
import com.sparta.hanghae.picturespot.repository.PwdCheckRepository;
//import com.sparta.hanghae.picturespot.repository.RefreshTokenRepository;
import com.sparta.hanghae.picturespot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.modelmapper.ModelMapper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
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
    //private final RefreshTokenRepository refreshTokenRepository;

    @Value("${spring.mail.username}")
    private String from;

    private static final String ADMIN_TOKEN = "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";

    // 회원가입
    public void signup(SignupRequestDto requestDto){

        String encodPassword = bCryptPasswordEncoder.encode(requestDto.getPassword());
        UserRole role = UserRole.USER;
        AuthProvider authProvider = AuthProvider.local;
        User user = new User(requestDto.getNickname(), requestDto.getEmail(), encodPassword, role, authProvider);

        userRepository.save(user);
    }

    // 로그인
    @Transactional
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail());
        if(user == null){
            throw new IllegalStateException("가입되지 않은 email 입니다.");
        }
        if(!bCryptPasswordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())){
            throw new IllegalStateException("잘못된 비밀번호 입니다.");
        }
        //TokenDto tokenDto = jwtTokenProvider.createToken(user.getEmail());

        // refresh Token 저장
//        RefreshToken refreshToken = new RefreshToken(user.getEmail(), tokenDto.getRefreshToken());
//        refreshTokenRepository.save(refreshToken);



        return new LoginResponseDto(jwtTokenProvider.createToken(user.getEmail()), user.getNickname(), "성공", user.getId(), user.getRole());
    }

    // @Vaild 에러체크
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();
        for(FieldError error : errors.getFieldErrors()){
            String validKeyName = error.getField();
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }

    // 닉네임 중복체크
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
        AuthProvider authProvider = AuthProvider.local;
        User user = new User(adminSignupRequestDto.getNickname(), adminSignupRequestDto.getEmail(), encodPassword, role, authProvider);
        userRepository.save(user);
    }


//    @Transactional
//    public TokenDto reissue(TokenDto tokenDto) {
//        //1. Refresh Token 검증
//        if (!jwtTokenProvider.validateToken(tokenDto.getRefreshToken())){
//            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
//        }
//
//        Authentication authentication = jwtTokenProvider.getAuthentication(tokenDto.getAccessToken());
//
//
//        // 3. Refresh Token 저장소에서 Member ID를 기반으로 Refresh Token 값 가져오기
//        RefreshToken refreshToken = refreshTokenRepository.findByTokenKey(authentication.getName());
//
//        // 위에서 유효기간이 끝나면 걸러주기 때문에 굳이 필요하진 않을것 같다.
//        LocalDateTime today = LocalDateTime.now();
//        LocalDateTime modiyfied = refreshToken.getModified();
//        long days = Duration.between(modiyfied, today).toDays();
//        if(days > 7){
//            throw new RuntimeException("Refresh Token 이 만료되었습니다.");
//        }
//
//        // redis 사용법에는 2가지 방법이 있다. 1) template사용하는 것과 2) repository 사용법
//
//        // template방법 사용시 redishash도 필요없고 repository도 필요없는것 같다.
//        /////////////////////////
//        System.out.println("refreshToken.getTokenValue(): " + refreshToken.getTokenValue());
//        System.out.println("tokenDto.getRefreshToken(): "+ tokenDto.getRefreshToken());
//
//        if (!refreshToken.getTokenValue().equals(tokenDto.getRefreshToken())){
//            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
//        }
//
//        // 새로운 토큰 생성
//        // authentication.getName으로 이메일 가져올 수 있다. 토큰 생성할때 이메일을 넣어서 생성해서 가능한것 같다.
//        TokenDto newTokenDto = jwtTokenProvider.createToken(authentication.getName());
//
//        // 저장소 정보 업데이트
//        refreshToken.updateValue(newTokenDto.getRefreshToken());
//
//
//
//        return newTokenDto;
//    }
}
