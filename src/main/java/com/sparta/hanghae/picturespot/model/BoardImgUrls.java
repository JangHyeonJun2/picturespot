package com.sparta.hanghae.picturespot.model;

import com.sparta.hanghae.picturespot.dto.request.board.BoardUpdateRequestDto;
import com.sparta.hanghae.picturespot.dto.request.img.BoardImgCommonRequestDto;
import com.sparta.hanghae.picturespot.dto.response.board.BoardDetailCommentsDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    @Builder
    public BoardImgUrls(String imgUrl, Board board) {
        this.imgUrl = imgUrl;
        this.board = board;
    }

    public static Set<BoardImgCommonRequestDto> toDtoList(Set<BoardImgUrls> dtos) {
        Set<BoardImgCommonRequestDto> imgCommonRequestDtos = new HashSet<>();
        for (BoardImgUrls boardImgUrl : dtos) {
            imgCommonRequestDtos.add(new BoardImgCommonRequestDto(boardImgUrl));
        }
        return imgCommonRequestDtos;
    }
}
