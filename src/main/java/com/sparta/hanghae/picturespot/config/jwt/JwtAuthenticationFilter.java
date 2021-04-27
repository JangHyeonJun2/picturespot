package com.sparta.hanghae.picturespot.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.hanghae.picturespot.config.auth.PrincipalDetails;
import com.sparta.hanghae.picturespot.dto.UserInfoDto;
import com.sparta.hanghae.picturespot.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;

// login 요청해서 username,password 전송하면(post)
// 스프링 시큐리티에서 UsernamePasswordAuthenticationFilter가 동작을함 그런데 지금은 formLogin을 disable을 해놨기 떄문에 작동안함
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    // login 요청을 하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("JwtAuthenticationFilter : 로그인 시도중");
        // 1. username,password 받아서
        try {
            ObjectMapper om = new ObjectMapper();
            User user = om.readValue(request.getInputStream(), User.class);
            System.out.println(user);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

            // PrincipalDetailsService loadUserByUsername() 함수가 실행된 후 정상이면 authentication이 리턴됨
            // DB에 있는 username과 password가 일치한다.
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
            System.out.println("로그인완료됨 :"+principalDetails.getUser().getUsername()); // 값이 찍히면 로그인이 정상적을 되었다는것

            // authentication 객체가 session 영역에 저장을 해야하고 그 방법이 return 해주면 됨
            // 리턴의 이유는 권한 관리를 security가 대신 해주기 때문에 편하려고 하는거임
            // 굳이 jwt토큰을 사용하면서 세션을 만들 이유가 없음 근데 단지 권한 처리때문에 session 넣어 줌
            return authentication;
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("==============================");
        return null;
    }

    // attemptAuthentication실행 후 인증이 정상적으로 되었으면 successfulAuthentication 함수가 실행됨
    // JWT토큰을 만들어서 request요청한 사용자에게 JWT 토큰을 response해주면 됨
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("successfulAuthentication 실행됨 : 인증이 완료되었다는 뜻");
        PrincipalDetails principalDetails = (PrincipalDetails)authResult.getPrincipal();
        //super.successfulAuthentication(request, response, chain, authResult);

        // RSA방식은 아니구 Hash 암호 방식
        String jwtToken = JWT.create()
                .withSubject(JwtProperties.SECRET)
                .withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.EXPIRATION_TIME)) // 토큰 시간 300분
                .withClaim("id",principalDetails.getUser().getId())
                .withClaim("email",principalDetails.getUser().getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        // body에 담을 유저 정보 생성
        ObjectMapper objectMapper = new ObjectMapper();

        UserInfoDto userInfoDto = new UserInfoDto(principalDetails.getUser().getId(), principalDetails.getUser().getNickname(), principalDetails.getUser().getUsername(), JwtProperties.TOKEN_PREFIX+jwtToken);
        String userInfoJson = objectMapper.writeValueAsString(userInfoDto);

        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX+jwtToken); // 클라이언트에 보내줄 헤더에 토큰 넣어서 보내줌
        response.addHeader("Content-type", "applacation/json");
        response.getWriter().write(userInfoJson);
    }
}
