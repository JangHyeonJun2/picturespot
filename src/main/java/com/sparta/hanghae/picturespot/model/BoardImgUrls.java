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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARDIMGURLS_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    private String imgUrl;

    private Long ArticleId;

    @Builder
    public BoardImgUrls(String imgUrl, Long boardId) {
        this.imgUrl = imgUrl;
        this.ArticleId = boardId;
    }
}
