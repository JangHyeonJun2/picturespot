package com.sparta.hanghae.picturespot.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Entity
public class Board extends Timestamped{
    @Id
    @GeneratedValue
    @Column(name = "BOARD_ID")
    private Long id;

    private String title;

    private String content;

    private String category;

    private String imgUrl;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private int likeCount;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
}
