package com.sparta.hanghae.picturespot.repository;

import com.sparta.hanghae.picturespot.model.PwdCheck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PwdCheckRepository extends JpaRepository<PwdCheck, Long> {
    PwdCheck findByEmail(String email);
}
