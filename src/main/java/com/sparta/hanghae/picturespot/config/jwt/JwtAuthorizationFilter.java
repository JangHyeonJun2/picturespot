package com.sparta.hanghae.picturespot.config.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.sparta.hanghae.picturespot.config.auth.PrincipalDetails;
import com.sparta.hanghae.picturespot.model.User;
import com.sparta.hanghae.picturespot.repository.UserRepository;
import org.json.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

// 시큐리티가 filter 가지고 있는데 그 필터중에 BasicAuthenticationFilter 라는 것이 있음
// 권한이나 인증이 필요한 특정 주소를 요청했을 때 위 필터를 무조건 타게 되어있음
// 만약에 권한이 인증이 필요한 주소가 아니라면 이 필터를 타지 않는다.
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    // 인증이나 권한이 필요한 주소요청이 있을때 해당 필터를 타게됨
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {


        System.out.println("인증이나 권한이 필요한 주소 요청이 됨");

        String jwtHeader = request.getHeader(JwtProperties.HEADER_STRING);

        // jwt토큰을 검증해서 정상적인 사용자인지 확인
        if(jwtHeader == null || !jwtHeader.startsWith(JwtProperties.TOKEN_PREFIX)){
            chain.doFilter(request, response);
            return;
        }

        // jwt 토큰을 검증해서 정상적인 사용자 인지 확인
        String jwtToken = request.getHeader(JwtProperties.HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX, "");

        // 토큰 만료되었을때 error 잡기위해 try catch
        try {

            String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(jwtToken).getClaim("username").asString();

            // 서명이 정상적으로 됨
            if (username != null) {
                System.out.println("서명 정상");
                User userEntity = userRepository.findByUsername(username);

                PrincipalDetails principalDetails = new PrincipalDetails(userEntity);

                //jwt 토큰 서명을 통해서 서명이 정상이면 Authentication 객체를 만들어준다.
                Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

                // 강제로 시큐리티의 세션에 접근하여 Authentication 객체를 저장
                SecurityContextHolder.getContext().setAuthentication(authentication);

                chain.doFilter(request, response);

            }
        }catch (Exception e){
            JSONObject json = new JSONObject();
            json.put("message", "tokenExpired");
            PrintWriter out = response.getWriter();
            out.print(json);
        }
    }

}
