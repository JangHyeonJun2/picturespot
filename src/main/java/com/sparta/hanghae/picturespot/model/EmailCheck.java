package com.sparta.hanghae.picturespot.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class EmailCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMAILCHECK_ID")
    private Long id;

    private String email;

    private String authCode;


    public EmailCheck(String email, String auth) {
        this.email = email;
        this.authCode = auth;
    }

    public void update(String Y) {
        this.authCode = Y;
    }
}
