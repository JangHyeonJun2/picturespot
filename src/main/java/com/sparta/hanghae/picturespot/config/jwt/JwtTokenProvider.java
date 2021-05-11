package com.sparta.hanghae.picturespot.config.jwt;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider { // 토큰 생성, 검증

    private String secretKey = "ABCA7D35A0B04018B865E0817E1A41374FB06737CF00641E2A781F631B61C9AC";
    private final UserDetailsService userDetailsService;//토큰에 저장된 유저 정보를 활용해야 하기 때문에 CustomUserDetatilService 라는 이름의 클래스를 만들고 UserDetailsService를 상속받아 재정의 하는 과정을 진행합니다.

    @PostConstruct // 서버가 돌아가면 제일 먼저 실행시키는 어노테이션
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String userPk){
        Claims claims = Jwts.claims().setSubject(userPk);
        Date now = new Date();
        // token 발급 후 유효시간 30분 테스트만 임시로 300분
        long tokenValidTime = 30 * 60 * 10000L;
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }


    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // Request의 Header에서 token 값을 가져옵니다. "X-AUTH-TOKEN" : "TOKEN값'
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
//        } catch (SignatureException e) {
//            throw new SignatureException("token에러");
//        } catch (MalformedJwtException e) {
//            throw new MalformedJwtException("token에러");
//        } catch (ExpiredJwtException e) {
//            throw new IllegalStateException("token에러");
//        } catch (UnsupportedJwtException e) {
//            throw new UnsupportedJwtException("token에러");
//        } catch (IllegalArgumentException e) {
//            throw new IllegalArgumentException("token에러");
//        }
        } catch (Exception e) {
            return false;
        }
    }



}
