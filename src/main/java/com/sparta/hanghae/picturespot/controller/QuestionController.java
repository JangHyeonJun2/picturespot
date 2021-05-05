package com.sparta.hanghae.picturespot.controller;

import com.sparta.hanghae.picturespot.dto.request.question.QuestionRequestDto;
import com.sparta.hanghae.picturespot.dto.response.question.QuestionResponseDto;
import com.sparta.hanghae.picturespot.model.User;
import com.sparta.hanghae.picturespot.responseentity.CustomExceptionController;
import com.sparta.hanghae.picturespot.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class QuestionController {

    private final QuestionService questionService;
    private final CustomExceptionController customExceptionController;

    //문의하기 리스트
    @GetMapping("/qna")
    public ResponseEntity getAllQuestions(@RequestParam("page") int page, @RequestParam("size") int size){
        //----/qna?page={page}&size={size}
        page = page - 1;
        List<QuestionResponseDto> questionResponseDtos = questionService.getAllQuestions(page, size);
        return customExceptionController.ok("문의하기 리스트", questionResponseDtos);
    }

    //문의하기 상세
    @GetMapping("/qna/{qnaId}/detail")
    public ResponseEntity getQDetail(@PathVariable Long qnaId){
        QuestionResponseDto questionResponseDto = questionService.getQDetail(qnaId);
        return customExceptionController.ok("문의하기 상세", questionResponseDto);
    }

    //문의하기 글쓰기
    @PostMapping("/qna")
    public ResponseEntity createQuestion(@RequestBody QuestionRequestDto questionRequestDto, @AuthenticationPrincipal User user){
        return questionService.createQuestion(questionRequestDto, user);
    }

    //문의하기 수정
    @PutMapping("/qna/{qnaId}")
    public ResponseEntity updateQuestion(@PathVariable Long qnaId, @RequestBody QuestionRequestDto questionRequestDto, @AuthenticationPrincipal User user){
        return questionService.updateQuestion(qnaId, questionRequestDto, user);
    }

    //문의하기 삭제
    @DeleteMapping("/qna/{qnaId}")
    public ResponseEntity deleteQuestion(@PathVariable Long qnaId, @AuthenticationPrincipal User user){
        return questionService.deleteQuestion(qnaId, user);
    }
}
