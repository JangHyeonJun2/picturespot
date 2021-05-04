package com.sparta.hanghae.picturespot.repository;

import com.sparta.hanghae.picturespot.model.Board;
import com.sparta.hanghae.picturespot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByUserIdOrderByModifiedDesc(Long userId);
    Optional<Board> findByIdOrderByModifiedDesc(Long boardId);
    //List<Board> findAllByUserId(Long id);
    Optional<Board> findById(Long BoardId);

}

