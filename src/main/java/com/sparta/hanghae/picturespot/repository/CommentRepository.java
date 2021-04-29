package com.sparta.hanghae.picturespot.repository;

import com.sparta.hanghae.picturespot.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByBoardIdOrderByModifiedDesc(Long boardId);
}
