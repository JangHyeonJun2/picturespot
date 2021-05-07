package com.sparta.hanghae.picturespot.model;

import com.sparta.hanghae.picturespot.dto.request.mypage.NicknameRequestDto;
import com.sparta.hanghae.picturespot.dto.request.mypage.ProfileRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class User extends Timestamped  {

    //private Map<String, Object> attributes;

    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private Long id;
    @Column(nullable = false)
    private String nickname;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    private String introduceMsg;

    private String imgUrl;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    @Enumerated(value = EnumType.STRING)
    private AuthProvider provider;

    private String providerId;

    public User(String nickname, String email, String password, UserRole role) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(String nickname, String email, String password, UserRole role, AuthProvider provider, String providerId) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;

    }

    public void updatePw(String password) {
        this.password = password;
    }

    public void updateProfile(ProfileRequestDto profileRequestDto) {
        this.imgUrl = profileRequestDto.getImgUrl();
        this.introduceMsg = profileRequestDto.getIntroduceMsg();
    }

    public void updateNick(NicknameRequestDto nicknameRequestDto) {
        this.nickname = nicknameRequestDto.getNickname();
    }
}

    ////////////// UserDetails Override /////////////////

//    private static final String ROLE_PREFIX = "ROLE_";
//
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        UserRole userRole = this.role;
//        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(ROLE_PREFIX + userRole.toString());
//        Collection<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(authority);
//        return authorities;
//    }
//
//    @Override
//    public String getUsername() {
//        return null;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }


    ////////////// OAuth2User Override /////////////////
//    @Override
//    public Map<String, Object> getAttributes() {
//        return null;
//    }
//
//    @Override
//    public String getName() {
//        return null;
//    }

