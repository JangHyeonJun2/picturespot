package com.sparta.hanghae.picturespot.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@NoArgsConstructor
@Getter
@Entity
public class User extends Timestamped{
    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private Long id;

    private String nickname;

    private String email;

    private String password;

    private String introduceMsg;

    private String imgUrl;
//    private enum ROLE
}
