package com.sparta.hanghae.picturespot.model;

import com.sparta.hanghae.picturespot.dto.request.board.BoardUpdateRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

//    @BatchSize(size = 900)
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<BoardImgUrls> boardImgUrls = new HashSet<>();

//    @BatchSize(size = 900)
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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
