package com.sparta.hanghae.picturespot.model;

import com.sparta.hanghae.picturespot.dto.request.board.BoardUpdateRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class BoardImgUrls {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    private String imgUrl;

    @Builder
    public BoardImgUrls(Board board, String imgUrl) {
        this.board = board;
        this.imgUrl = imgUrl;
    }
}
