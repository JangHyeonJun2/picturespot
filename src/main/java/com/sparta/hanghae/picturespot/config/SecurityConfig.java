package com.sparta.hanghae.picturespot.config;


import com.sparta.hanghae.picturespot.config.jwt.JwtAuthenticationFilter;
import com.sparta.hanghae.picturespot.config.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
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
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
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
        // 시큐리티 필터체인이 기본적으로 직접 만든 필터보다 먼저 동작한다.
        // 시큐리티 보다 먼저 동작하게 하고 싶으면 addFilterBefore로 특정 필터 실행전에 실행되게 설정해야 한다.

        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)// 세션을 사용하지 않곘다.
        .and()
                .addFilter(corsFilter) // @CrossOrigin는 인증이 안필요하면 controller에 달아서 사용해도되지만 인증이 필요한 경우는 시큐리티 필터에 추가해줘야 한다.
                .formLogin().disable()
                .httpBasic().disable() // Authorization 키값에 id와 비밀번호를 담아서 보내는 방식이다.
                .headers().frameOptions().disable().and()
                // 권한 설정
                .authorizeRequests()
                .antMatchers("/user/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class);
    }
}
