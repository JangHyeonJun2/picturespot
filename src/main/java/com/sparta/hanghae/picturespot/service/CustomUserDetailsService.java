package com.sparta.hanghae.picturespot.service;

import com.sparta.hanghae.picturespot.model.UserPrincipal;
import com.sparta.hanghae.picturespot.model.User;
import com.sparta.hanghae.picturespot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService { //db에서 찾기

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        return UserPrincipal.create(user);
    }
}