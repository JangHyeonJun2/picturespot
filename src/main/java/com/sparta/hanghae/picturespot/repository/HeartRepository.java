package com.sparta.hanghae.picturespot.repository;


import com.sparta.hanghae.picturespot.model.Heart;
import com.sparta.hanghae.picturespot.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HeartRepository extends JpaRepository<Heart, Long> {
    //List<Heart> findAllByUserId(Long userId);
    List<Heart> findAllByBoardId(Long boardId);
    List<Heart> findAllByUser(User user);

    Page<Heart> findByBoardIdLessThanAndUserIdOrderByBoardIdDesc(Long lastId, Long userId, Pageable pageable);

    boolean existsByBoardIdAndUserId(Long boardId, Long loginUserId);
    Heart findByBoardIdAndUserId(Long boardId, Long loginUserId);
}

