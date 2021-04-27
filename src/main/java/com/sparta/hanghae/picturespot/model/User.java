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
    @Column(nullable = false)
    private String nickname;
    @Column(nullable = false)
    private String username; // email jwt UsernamePasswordAuthenticationFilter 에 username이라고 박혀있어서 바꾸는 방법을 몰라서 dto로는 email로 받고 엔티티에는 username으로 저장
    @Column(nullable = false)
    private String password;

    private String introduceMsg;

    private String imgUrl;
//    private enum ROLE

    public User (String nickname, String email, String password){
        this.nickname = nickname;
        this.username = email;
        this.password = password;
    }
}
