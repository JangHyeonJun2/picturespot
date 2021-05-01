package com.sparta.hanghae.picturespot.repository;

import com.sparta.hanghae.picturespot.model.Board;
import com.sparta.hanghae.picturespot.model.Heart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HeartRepository extends JpaRepository<Heart, Long> {
    List<Heart> findAllByUserId(Long userId);
    List<Heart> findAllByBoardId(Long boardId);

    boolean existsByBoardIdAndUserId(Long boardId, Long loginUserId);
    int findLikeCountByBoardId(Long boardId);
}
