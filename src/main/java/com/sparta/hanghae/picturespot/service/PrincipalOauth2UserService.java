package com.sparta.hanghae.picturespot.service;

import com.sparta.hanghae.picturespot.model.*;

import com.sparta.hanghae.picturespot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Provider;
import java.util.Map;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    public PrincipalOauth2UserService(@Lazy BCryptPasswordEncoder bCryptPasswordEncoder,@Lazy UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    // 받은 userRequest 데이터에 대한 후처리되는 함수수
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("getClientRegistration :"+userRequest.getClientRegistration());
        System.out.println("getAccessToken :"+userRequest.getAccessToken());
        System.out.println("getAttributes :"+oAuth2User.getAttributes());
        System.out.println("getAttributes :"+oAuth2User);

        OAuth2UserInfo oAuth2UserInfo = null;
        if(userRequest.getClientRegistration().getRegistrationId().equals("naver")){
            System.out.println((Map)oAuth2User.getAttributes().get("response"));
            oAuth2UserInfo = new NaverUserInfo(oAuth2User.getAttributes());
        }

        String strProvider = oAuth2UserInfo.getProvider(); // naver
        AuthProvider provider = null;
        if(strProvider.equals("naver")){
            provider= AuthProvider.NAVER;
        }
        else if(strProvider.equals("google")){
            provider= AuthProvider.GOOGLE;
        }
        else if (strProvider.equals("kakao")){
            provider= AuthProvider.KAKAO;
        }
        String providerId = oAuth2UserInfo.getProviderId();
        System.out.println("providerId :"+providerId);

        String email = oAuth2UserInfo.getEmail();
        System.out.println("email :"+email);

        String nickname = oAuth2UserInfo.getName();
        System.out.println("nickname :"+nickname);

        String password = bCryptPasswordEncoder.encode("test1234567");
        System.out.println("password :"+password);
        UserRole role = UserRole.USER;

        User user = userRepository.findByEmail(email);

        if(user == null){ // 회원가입이 안되어있다 => 회원가입 시키면 된다.
            user = new User(nickname, email, password, role, provider, providerId);
            userRepository.save(user);
        }

        return new PrincipalDetails(user, oAuth2User.getAttributes());
    }
}
