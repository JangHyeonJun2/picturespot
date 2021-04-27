package com.sparta.hanghae.picturespot.model;

import com.sparta.hanghae.picturespot.dto.requestDto.QCommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class QComment extends Timestamped{
    @Id
    @GeneratedValue
    private Long id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "QUESTION_ID")
    private Question question;


    public QComment(Question question, QCommentRequestDto qCommentRequestDto){
        this.content = qCommentRequestDto.getContent();
        //this.user = user;
        this.question = question;
    }

    public void update(QCommentRequestDto qCommentRequestDto){
        this.content = qCommentRequestDto.getContent();
    }
}
