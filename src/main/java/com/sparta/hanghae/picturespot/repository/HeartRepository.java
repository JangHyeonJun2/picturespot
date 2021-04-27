package com.sparta.hanghae.picturespot.repository;

import com.sparta.hanghae.picturespot.model.Heart;
import com.sparta.hanghae.picturespot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeartRepository extends JpaRepository<Heart, Long> {
    Heart findByUserAndLiked(User user, Boolean liked);
}
