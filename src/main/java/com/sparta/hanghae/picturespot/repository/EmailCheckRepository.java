package com.sparta.hanghae.picturespot.repository;

import com.sparta.hanghae.picturespot.model.EmailCheck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailCheckRepository extends JpaRepository<EmailCheck, Long> {
    EmailCheck findByEmail(String email);

    void deleteByEmail(String email);
}
