package com.sparta.hanghae.picturespot.dto.request.img;

import com.sparta.hanghae.picturespot.model.Board;
import com.sparta.hanghae.picturespot.model.BoardImgUrls;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardImgSaveRequestDto {
    private Board board;
    private String imgUrl;

    @Builder
    public BoardImgSaveRequestDto(Board board, String imgUrl) {
        this.board = board;
        this.imgUrl = imgUrl;
    }

    public BoardImgUrls toEntity() {
        return BoardImgUrls.builder()
                .board(board)
                .imgUrl(imgUrl)
                .build();
    }
}
