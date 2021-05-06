package com.sparta.hanghae.picturespot.dto.response.img;

import com.sparta.hanghae.picturespot.model.Board;
import com.sparta.hanghae.picturespot.model.BoardImgUrls;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardImgSaveReponseDto {
    private Board board;
    private String imgUrl;

    @Builder
    public BoardImgSaveReponseDto(Board board, String imgUrl) {
        this.board = board;
        this.imgUrl = imgUrl;
    }
}
