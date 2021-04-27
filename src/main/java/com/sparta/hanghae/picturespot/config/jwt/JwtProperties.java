package com.sparta.hanghae.picturespot.config.jwt;

public interface JwtProperties {
    String SECRET = "sflash"; // 서버만 알고있는 비밀 값
    int EXPIRATION_TIME = 600000*30; // 300분 테스트할 때만 길게해놈
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
