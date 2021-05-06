package com.sparta.hanghae.picturespot.model;

import com.sparta.hanghae.picturespot.dto.request.question.QuestionRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Question extends Timestamped{
    @Id
    @GeneratedValue
    @Column(name = "QUESTION_ID")
    private Long id;

    private String title;

    private String content;


    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;


    public Question(QuestionRequestDto questionRequestDto, User user){
        this.user = user;
        this.title = questionRequestDto.getTitle();
        this.content = questionRequestDto.getContent();
    }

    public void update(QuestionRequestDto questionRequestDto){
        this.title = questionRequestDto.getTitle();
        this.content = questionRequestDto.getContent();
    }
}
