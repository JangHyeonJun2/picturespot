package com.sparta.hanghae.picturespot.repository;

import com.sparta.hanghae.picturespot.model.QComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QCommentRepository extends JpaRepository<QComment, Long> {
    List<QComment> findAllByQuestionIdOrderByModifiedDesc(Long questionId);
    Optional<QComment> findByQuestionIdAndId(Long questionId, Long Id);
}
