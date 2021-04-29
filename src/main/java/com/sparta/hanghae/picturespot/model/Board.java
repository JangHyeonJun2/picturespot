package com.sparta.hanghae.picturespot.model;

import lombok.Builder;
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

    @Builder
    public Board(String title, String content, String category, String imgUrl, BigDecimal latitude, BigDecimal longitude, int likeCount, User user) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.imgUrl = imgUrl;
        this.latitude = latitude;
        this.longitude = longitude;
        this.likeCount = likeCount;
        this.user = user;
    }
}
