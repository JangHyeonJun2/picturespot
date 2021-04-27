package com.sparta.hanghae.picturespot.service;

import com.sparta.hanghae.picturespot.dto.SignupRequestDto;
import com.sparta.hanghae.picturespot.model.User;
import com.sparta.hanghae.picturespot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void signup(SignupRequestDto requestDto){
        String encodPassword = bCryptPasswordEncoder.encode(requestDto.getPassword());
        User user = new User(requestDto.getNickname(), requestDto.getEmail(), encodPassword);
        userRepository.save(user);
    }

}
