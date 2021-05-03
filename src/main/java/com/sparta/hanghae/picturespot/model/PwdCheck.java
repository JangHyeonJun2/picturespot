package com.sparta.hanghae.picturespot.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class PwdCheck {

    @Id @GeneratedValue
    private Long id;

    private String email;

    private String authCode;

    public PwdCheck(String email, String auth) {
        this.email = email;
        this.authCode = auth;
    }

    public void update(String auth) {
        this.authCode = auth;
    }
}
