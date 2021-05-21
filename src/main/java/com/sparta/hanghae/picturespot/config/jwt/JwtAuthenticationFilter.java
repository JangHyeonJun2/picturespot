package com.sparta.hanghae.picturespot.config.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        try {
            // 헤더에서 JWT 를 받아옵니다.
            String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
            System.out.println("===============================");
            System.out.println("token :"+token);
            System.out.println("===============================");
            // 유효한 토큰인지 확인합니다.
            if ((token != null) && (jwtTokenProvider.validateToken(token))) {
                // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                // SecurityContext 에 Authentication 객체를 저장합니다.
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            chain.doFilter(request, response);
        }
//        catch (Exception e){
//            //jwtTokenProvider.resolveToken((HttpServletRequest) request)가 null일 경우는 token이 필요하지 않은 경우이고 "null"일 경우는 토큰이 필요하고 토큰을 보내지만 토큰이 null로 온 경우이다.
//            if ((jwtTokenProvider.resolveToken((HttpServletRequest) request)).equals("null")){
//                //log.error(jwtTokenProvider.resolveToken((HttpServletRequest) request));
//                throw new MalformedJwtException("에러");
////                JSONObject json = new JSONObject();
////                json.put("message", "tokenInvalid");
////                PrintWriter out = response.getWriter();
////                out.print(json);
//            }
////            else{
////                //log.error(jwtTokenProvider.resolveToken((HttpServletRequest) request));
////                JSONObject json = new JSONObject();
////                json.put("message", "tokenExpired");
////                PrintWriter out = response.getWriter();
////                out.print(json);
////            }
//
//        }
//    }
}

