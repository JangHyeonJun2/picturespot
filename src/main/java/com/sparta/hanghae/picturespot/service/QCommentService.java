package com.sparta.hanghae.picturespot.service;

import com.sparta.hanghae.picturespot.dto.request.question.QCommentRequestDto;
import com.sparta.hanghae.picturespot.dto.response.question.Message;
import com.sparta.hanghae.picturespot.model.QComment;
import com.sparta.hanghae.picturespot.model.Question;
import com.sparta.hanghae.picturespot.model.User;
import com.sparta.hanghae.picturespot.repository.QCommentRepository;
import com.sparta.hanghae.picturespot.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class QCommentService {

    private final QuestionRepository questionRepository;
    private final QCommentRepository qCommentRepository;

    //----문의하기 댓글은 관리자만 작성할 수 있음----//

    //문의하기 댓글 쓰기
    @Transactional
    public ResponseEntity createQComment(Long qnaId, QCommentRequestDto qCommentRequestDto, User user){
        String role = user.getRole().toString();
        if (!role.equals("ADMIN")){
            throw new IllegalArgumentException("관리자만 댓글을 작성할 수 있습니다");
        }
        Question question = questionRepository.findById(qnaId).orElseThrow(
                () -> new IllegalArgumentException("게시글이 없습니다")
        );
        QComment qComment = new QComment(question, qCommentRequestDto, user);
        qCommentRepository.save(qComment);
        Message message = new Message("댓글이 등록되었습니다.");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //문의하기 댓글 수정
    @Transactional
    public ResponseEntity updateQComment(Long qnaId, Long qcommentId, QCommentRequestDto qCommentRequestDto, User user){
        String role = user.getRole().toString();
        if (!role.equals("ADMIN")){
            throw new IllegalArgumentException("관리자만 댓글을 수정할 수 있습니다");
        }
        QComment qComment = qCommentRepository.findByQuestionIdAndId(qnaId, qcommentId).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 없습니다.")
        );
        qComment.update(qCommentRequestDto);
        Message message = new Message("댓글이 수정되었습니다.");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //문의하기 댓글 삭제
    @Transactional
    public ResponseEntity deleteQComment(Long qcommentId, Long qnaId, User user){
        String role = user.getRole().toString();
        if (!role.equals("ADMIN")){
            throw new IllegalArgumentException("관리자만 댓글을 삭제할 수 있습니다");
        }
        QComment qComment = qCommentRepository.findByQuestionIdAndId(qnaId, qcommentId).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 없습니다.")
        );
        qCommentRepository.delete(qComment);
        Message message = new Message("댓글이 삭제되었습니다.");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
