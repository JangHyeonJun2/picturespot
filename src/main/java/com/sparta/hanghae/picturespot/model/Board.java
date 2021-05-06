package com.sparta.hanghae.picturespot.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.annotation.Resource;
import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Entity
//imgUrl을 분리함.
public class Board extends Timestamped{
    @Id
    @GeneratedValue
    @Column(name = "BOARD_ID")
    private Long id;

    private String title;

    private String content;

    private String category;

//    @Column(columnDefinition="TEXT")
//    @ElementCollection
//    private String[] imgUrls;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private String spotName;


    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Builder
    public Board(String title, String content, String category,  BigDecimal latitude, BigDecimal longitude, String spotName, User user) {
        this.title = title;
        this.content = content;
        this.category = category;

//        this.imgUrls = imgUrls;
        this.latitude = latitude;
        this.longitude = longitude;
        this.spotName = spotName;
        this.user = user;
    }
}
