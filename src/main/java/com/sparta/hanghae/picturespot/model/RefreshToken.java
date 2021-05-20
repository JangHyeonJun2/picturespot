//package com.sparta.hanghae.picturespot.model;
//
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import org.springframework.data.redis.core.RedisHash;
//
//import javax.persistence.*;
//
//@Getter
//@NoArgsConstructor
//@Entity
//public class RefreshToken extends Timestamped {
//
//    @Id
//    private String tokenKey;
//    private String tokenValue;
//
//    public void updateValue(String token){
//        this.tokenValue = token;
//    }
//
//    public RefreshToken(String tokenKey, String value){
//        this.tokenKey = tokenKey;
//        this.tokenValue = value;
//    }
//}
