package com.sparta.hanghae.picturespot.repository;

import com.sparta.hanghae.picturespot.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
