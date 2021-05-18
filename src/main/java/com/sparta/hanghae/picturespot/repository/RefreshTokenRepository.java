package com.sparta.hanghae.picturespot.repository;

import com.sparta.hanghae.picturespot.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    RefreshToken findByTokenKey(String key);

    void deleteByTokenKey(String name);
}

