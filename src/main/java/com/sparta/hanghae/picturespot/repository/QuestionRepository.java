package com.sparta.hanghae.picturespot.repository;

import com.sparta.hanghae.picturespot.model.Question;
import com.sparta.hanghae.picturespot.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Page<Question> findAllByOrderByModifiedDesc(Pageable pageable);
    //List<Question> findAllByOrderByModifiedDesc();
    Optional<Question> findById(Long qnaId);
    Optional<Question> findByUserAndId(User user, Long qnaId);
}
