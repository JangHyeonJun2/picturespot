package com.sparta.hanghae.picturespot.repository;

import com.sparta.hanghae.picturespot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    //User findByUser(User user);

    User findByEmail(String email);
    User findByNickname(String nickname);
}
