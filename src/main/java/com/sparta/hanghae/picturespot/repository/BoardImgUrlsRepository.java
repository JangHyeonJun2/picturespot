package com.sparta.hanghae.picturespot.repository;

import com.sparta.hanghae.picturespot.model.BoardImgUrls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardImgUrlsRepository extends JpaRepository<BoardImgUrls, Long> {
    List<BoardImgUrls> findAllByBoardId(Long boardId);

}
