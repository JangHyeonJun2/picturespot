package com.sparta.hanghae.picturespot.service;

import com.sparta.hanghae.picturespot.dto.request.question.QCommentRequestDto;
import com.sparta.hanghae.picturespot.dto.response.question.Message;
import com.sparta.hanghae.picturespot.dto.response.question.QCommentResponseDto;
import com.sparta.hanghae.picturespot.model.QComment;
import com.sparta.hanghae.picturespot.model.Question;
import com.sparta.hanghae.picturespot.model.User;
import com.sparta.hanghae.picturespot.model.UserPrincipal;
import com.sparta.hanghae.picturespot.repository.QCommentRepository;
import com.sparta.hanghae.picturespot.repository.QuestionRepository;
import com.sparta.hanghae.picturespot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class QCommentService {

    private final QuestionRepository questionRepository;
    private final QCommentRepository qCommentRepository;
    private final UserRepository userRepository;

    //----문의하기 댓글은 관리자만 작성할 수 있음----//

    //문의하기 댓글 쓰기
    @Transactional
    public QCommentResponseDto createQComment(Long qnaId, QCommentRequestDto qCommentRequestDto, Errors errors, UserPrincipal user){
        if(errors.hasErrors()){
            Map<String, String> error = validateHandling(errors);
            throw new IllegalArgumentException("댓글을 입력해주세요");
//            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        User findUser = userRepository.findById(user.getId()).orElseThrow(
                ()->new IllegalArgumentException("해당 사용자가 없습니다."));
        String role = findUser.getRole().toString();
        if (!role.equals("ADMIN")){
            throw new IllegalArgumentException("관리자만 댓글을 작성할 수 있습니다");
        }
        Question question = questionRepository.findById(qnaId).orElseThrow(
                () -> new IllegalArgumentException("게시글이 없습니다")
        );
        QComment qComment = new QComment(question, qCommentRequestDto, findUser);
        qCommentRepository.save(qComment);
        return new QCommentResponseDto(qComment);
    }

    //문의하기 댓글 수정
    @Transactional
    public QCommentResponseDto updateQComment(Long qnaId, Long qcommentId, QCommentRequestDto qCommentRequestDto, Errors errors, UserPrincipal user){
        if(errors.hasErrors()){
            Map<String, String> error = validateHandling(errors);
            throw new IllegalArgumentException("댓글을 입력해주세요");
        }
        User findUser = userRepository.findById(user.getId()).orElseThrow(
                ()->new IllegalArgumentException("해당 사용자가 없습니다."));
        String role = findUser.getRole().toString();
        if (!role.equals("ADMIN")){
            throw new IllegalArgumentException("관리자만 댓글을 수정할 수 있습니다");
        }
        QComment qComment = qCommentRepository.findByQuestionIdAndId(qnaId, qcommentId).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 없습니다.")
        );
        qComment.update(qCommentRequestDto);
        Message message = new Message("댓글이 수정되었습니다.");
        return new QCommentResponseDto(qComment);
    }

    //문의하기 댓글 삭제
    @Transactional
    public ResponseEntity deleteQComment(Long qcommentId, Long qnaId, UserPrincipal user){
        User findUser = userRepository.findById(user.getId()).orElseThrow(
                ()->new IllegalArgumentException("해당 사용자가 없습니다."));
        String role = findUser.getRole().toString();
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

    // @Vaild 에러체크
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();
        for(FieldError error : errors.getFieldErrors()){
            String validKeyName = error.getField();
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }
}
