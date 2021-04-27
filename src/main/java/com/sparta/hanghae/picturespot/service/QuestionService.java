package com.sparta.hanghae.picturespot.service;

import com.sparta.hanghae.picturespot.dto.reponseDto.QCommentResponseDto;
import com.sparta.hanghae.picturespot.dto.requestDto.QuestionRequestDto;
import com.sparta.hanghae.picturespot.dto.reponseDto.QuestionResponseDto;
import com.sparta.hanghae.picturespot.model.Message;
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
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QCommentRepository qCommentRepository;

    //문의하기 게시글 리스트
    public List<QuestionResponseDto> getAllQuestions(){
        List<QuestionResponseDto> questionResponseDtos = new ArrayList<>();
        List<Question> questionList = questionRepository.findAllByOrderByModifiedDesc();
        for (Question question : questionList){
            QuestionResponseDto questionDto = new QuestionResponseDto(question);
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
        List<QCommentResponseDto> qCommentReponseDtos = new ArrayList<>();
        for (QComment qComment : qCommentList){
            QCommentResponseDto qCommentDto = new QCommentResponseDto(qComment);
            qCommentReponseDtos.add(qCommentDto);
        }
        QuestionResponseDto questionReponseDto = new QuestionResponseDto(question, qCommentReponseDtos);
        return questionReponseDto;
    }

    //문의하기 글쓰기
    @Transactional
    public ResponseEntity createQuestion(QuestionRequestDto questionRequestDto){
        Question question = new Question(questionRequestDto);
        questionRepository.save(question);
        Message message = new Message("게시글이 작성되었습니다.");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //문의하기 수정
    @Transactional
    public ResponseEntity updateQuestion(Long qnaId, QuestionRequestDto questionRequestDto){
//        Question question = questionRepository.findByUserAndId(user, qnaId).orElseThrow(
//                () -> new IllegalArgumentException("작성자만 수정 가능")
//        );
        Question question = questionRepository.findById(qnaId).orElseThrow(
                () -> new IllegalArgumentException("작성자만 수정 가능")
        );
        question.update(questionRequestDto);
        Message message = new Message("게시글이 수정되었습니다.");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //문의하기 삭제
    @Transactional
    public ResponseEntity deleteQuestion(Long qnaId){
//        Question question = questionRepository.findByUserAndId(user, qnaId).orElseThrow(
//                () -> new IllegalArgumentException("작성자만 삭제 가능")
//        );
        Question question = questionRepository.findById(qnaId).orElseThrow(
                () -> new IllegalArgumentException("작성자만 삭제 가능")
        );
        List<QComment> qComments = qCommentRepository.findAllByQuestionIdOrderByModifiedDesc(qnaId);
        for (QComment qComment : qComments){
            qCommentRepository.delete(qComment);
        } //해당 게시물의 댓글 먼저 모두 삭제
        questionRepository.delete(question); //문의하기 게시물 삭제
        Message message = new Message("게시글이 삭제되었습니다.");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
