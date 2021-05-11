package com.sparta.hanghae.picturespot.model;

import com.sparta.hanghae.picturespot.dto.request.board.BoardUpdateRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
//imgUrl을 분리함.
public class Board extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_ID")
    private Long id;

    private String title;

    private String content;

    private String category;

    private double latitude;

    private double longitude;

    private String spotName;


    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<BoardImgUrls> boardImgUrls = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Heart> hearts = new ArrayList<>();

    @Builder
    public Board(String title, String content, String category,  double latitude, double longitude, String spotName, User user) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.latitude = latitude;
        this.longitude = longitude;
        this.spotName = spotName;
        this.user = user;
    }

    @Transactional
    public void update(BoardUpdateRequestDto boardUpdateRequestDto) {
        this.title = boardUpdateRequestDto.getTitle();
        this.content = boardUpdateRequestDto.getContent();
    }
}
