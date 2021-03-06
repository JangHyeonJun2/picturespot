package com.sparta.hanghae.picturespot.config;


import com.sparta.hanghae.picturespot.config.jwt.JwtAccessDeniedHandler;
import com.sparta.hanghae.picturespot.config.jwt.JwtAuthenticationEntryPoint;
import com.sparta.hanghae.picturespot.config.jwt.JwtAuthenticationFilter;
import com.sparta.hanghae.picturespot.config.jwt.JwtTokenProvider;
import com.sparta.hanghae.picturespot.config.oauth2.CustomOAuth2UserService;
import com.sparta.hanghae.picturespot.config.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.sparta.hanghae.picturespot.config.oauth2.OAuth2AuthenticationFailureHandler;
import com.sparta.hanghae.picturespot.config.oauth2.OAuth2AuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;
    private final JwtTokenProvider jwtTokenProvider;
    //private final PrincipalOauth2UserService principalOauth2UserService;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        // ???????????? ??????????????? ??????????????? ?????? ?????? ???????????? ?????? ????????????.
        // ???????????? ?????? ?????? ???????????? ?????? ????????? addFilterBefore??? ?????? ?????? ???????????? ???????????? ???????????? ??????.

        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)// ????????? ???????????? ?????????.
        .and()
                // exceptionHandling ?????? ?????? ?????? ???????????? ??????
                .exceptionHandling()
                //.authenticationEntryPoint(jwtAuthenticationEntryPoint)
                //.accessDeniedHandler(jwtAccessDeniedHandler)
                .and()
                .addFilter(corsFilter) // @CrossOrigin??? ????????? ??????????????? controller??? ????????? ????????????????????? ????????? ????????? ????????? ???????????? ????????? ??????????????? ??????.
                .formLogin().disable()
                .httpBasic().disable() // Authorization ????????? id??? ??????????????? ????????? ????????? ????????????.
                .headers().frameOptions().disable().and()
                // ?????? ??????
                .authorizeRequests()
                .antMatchers("/user/**").permitAll()
                .antMatchers(HttpMethod.POST,"/user/**").permitAll()
                .antMatchers("/index").permitAll()
                .antMatchers("/oauth/token").permitAll()
                .antMatchers("/oauth2/authorize/**").permitAll()
                .antMatchers("/login/oauth2/code/**").permitAll()
                .antMatchers(HttpMethod.GET, "/map/**").permitAll()
                .antMatchers(HttpMethod.GET, "/board/**").permitAll()
                .antMatchers(HttpMethod.POST, "/board/**").permitAll()
                .antMatchers(HttpMethod.GET, "/qna/**").permitAll()
                .antMatchers(HttpMethod.POST,"/qna/**").permitAll()
                .antMatchers(HttpMethod.GET,"/profile/**").permitAll()
                .antMatchers(HttpMethod.GET,"/story/**").permitAll()
                .antMatchers(HttpMethod.GET, "/profile").permitAll()
                .antMatchers(HttpMethod.POST,"/qcomment/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT,"/qcomment/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/qcomment/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .authorizationEndpoint()
                .baseUri("/oauth2/authorize")
                .authorizationRequestRepository(cookieAuthorizationRequestRepository())
                .and()
                .redirectionEndpoint()
                .baseUri("/login/oauth2/code/*")
                .and()
                .userInfoEndpoint()
                .userService(customOAuth2UserService)
                .and()
                .successHandler(oAuth2AuthenticationSuccessHandler)
                .failureHandler(oAuth2AuthenticationFailureHandler);


        http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class);
    }
}
