package com.sparta.hanghae.picturespot.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Heart {
    @Id
    @GeneratedValue
    private Long id;

    private boolean liked;

    private int likeCount;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    private Board board;
}
