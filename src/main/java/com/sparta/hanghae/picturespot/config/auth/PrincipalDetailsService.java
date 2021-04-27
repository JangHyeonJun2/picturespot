package com.sparta.hanghae.picturespot.config.auth;

import com.sparta.hanghae.picturespot.model.User;
import com.sparta.hanghae.picturespot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// http://localhost:8080/login 요청이 올때 동작한다. => 그런데 시큐리티에서 formLogin을 사용안한다 했기때문에 따로 필터를 만들어 줘야한다.
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("과연 username은? :"+username);
        User userEntity = userRepository.findByUsername(username);
        return new PrincipalDetails(userEntity);
    }
}
