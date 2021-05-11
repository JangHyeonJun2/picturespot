package com.sparta.hanghae.picturespot.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class RefreshToken {

    @Id @GeneratedValue
    private Long id;

    private String tokenKey;
    private String tokenValue;

    public void updateValue(String token){
        this.tokenValue = token;
    }

    public RefreshToken(String key, String value){
        this.tokenKey = key;
        this.tokenValue = value;
    }
}
