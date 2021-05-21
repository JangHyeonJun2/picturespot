package com.sparta.hanghae.picturespot.service;

import com.sparta.hanghae.picturespot.dto.response.question.QCommentResponseDto;
import com.sparta.hanghae.picturespot.dto.request.question.QuestionRequestDto;
import com.sparta.hanghae.picturespot.dto.response.question.QuestionResponseDto;
import com.sparta.hanghae.picturespot.dto.response.question.Message;
import com.sparta.hanghae.picturespot.model.QComment;
import com.sparta.hanghae.picturespot.model.Question;
import com.sparta.hanghae.picturespot.model.User;
import com.sparta.hanghae.picturespot.model.UserPrincipal;
import com.sparta.hanghae.picturespot.repository.QCommentRepository;
import com.sparta.hanghae.picturespot.repository.QuestionRepository;
import com.sparta.hanghae.picturespot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QCommentRepository qCommentRepository;
    private final UserRepository userRepository;

    //문의하기 게시글 리스트
    public List<QuestionResponseDto> getAllQuestions(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Question> questionList = questionRepository.findAllByOrderByModifiedDesc(pageable);
        List<QuestionResponseDto> questionResponseDtos = new ArrayList<>();
        for (Question question : questionList){
            QuestionResponseDto questionDto = new QuestionResponseDto(question, questionList.getTotalElements(), questionList.getTotalPages() );
            questionResponseDtos.add(questionDto);
        }
        return questionResponseDtos;
    }

    //문의하기 상세페이지 -> 댓글도 함께
    public QuestionResponseDto getQDetail(Long qnaId){
        Question question = questionRepository.findById(qnaId).orElseThrow(
                () -> new IllegalArgumentException("게시물이 없습니다")
        );
        List<QComment> qCommentList = qCommentRepository.findAllByQuestionIdOrderByModifiedDesc(question.getId());
        List<QCommentResponseDto> qCommentResponseDtos = new ArrayList<>();
        for (QComment qComment : qCommentList){
            QCommentResponseDto qCommentDto = new QCommentResponseDto(qComment);
            qCommentResponseDtos.add(qCommentDto);
        }
        return new QuestionResponseDto(question, qCommentResponseDtos);
    }

    //문의하기 글쓰기
    @Transactional
    public QuestionResponseDto createQuestion(QuestionRequestDto questionRequestDto, Errors errors, UserPrincipal user){
        if(errors.hasErrors()){
            Map<String, String> error = validateHandling(errors);
            throw new IllegalArgumentException("title과 content 모두 입력해주세요");
//            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        User findUser = userRepository.findById(user.getId()).orElseThrow(
                ()->new IllegalArgumentException("해당 사용자가 없습니다."));
        Question question = new Question(questionRequestDto, findUser);
        questionRepository.save(question);
        return new QuestionResponseDto(question);
    }

    //문의하기 수정
    @Transactional
    public QuestionResponseDto updateQuestion(Long qnaId, QuestionRequestDto questionRequestDto, Errors errors, UserPrincipal user){
        if(errors.hasErrors()){
            Map<String, String> error = validateHandling(errors);
            throw new IllegalArgumentException("title과 content 모두 입력해주세요");
        }
        User findUser = userRepository.findById(user.getId()).orElseThrow(
                ()->new IllegalArgumentException("해당 사용자가 없습니다."));
        Question question = questionRepository.findByUserAndId(findUser, qnaId).orElseThrow(
                () -> new IllegalArgumentException("작성자만 수정 가능")
        );
        question.update(questionRequestDto);
        return new QuestionResponseDto(question);
    }

    //문의하기 삭제
    @Transactional
    public ResponseEntity deleteQuestion(Long qnaId, UserPrincipal user){
        User findUser = userRepository.findById(user.getId()).orElseThrow(
                ()->new IllegalArgumentException("해당 사용자가 없습니다."));
        Question question = questionRepository.findByUserAndId(findUser, qnaId).orElseThrow(
                () -> new IllegalArgumentException("작성자만 삭제 가능")
        );
//        List<QComment> qComments = qCommentRepository.findAllByQuestionIdOrderByModifiedDesc(qnaId);
//        for (QComment qComment : qComments){
//            qCommentRepository.delete(qComment);
//        } //해당 게시물의 댓글 먼저 모두 삭제
        questionRepository.delete(question); //문의하기 게시물 삭제
        Message message = new Message("게시글이 삭제되었습니다.");
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


