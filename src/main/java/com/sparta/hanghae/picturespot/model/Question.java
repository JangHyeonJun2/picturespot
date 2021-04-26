package com.sparta.hanghae.picturespot.model;

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

    private String writer;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
}
