package com.sparta.hanghae.picturespot.repository;

import com.sparta.hanghae.picturespot.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByTokenKey(String key);
}

