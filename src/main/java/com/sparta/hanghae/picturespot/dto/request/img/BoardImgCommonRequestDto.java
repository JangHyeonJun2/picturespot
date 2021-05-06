package com.sparta.hanghae.picturespot.dto.request.img;

import com.sparta.hanghae.picturespot.model.Board;
import com.sparta.hanghae.picturespot.model.BoardImgUrls;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class BoardImgCommonRequestDto {
    private Board board;
    private String imgUrl;

    @Builder
    public BoardImgCommonRequestDto(Board board, String imgUrl) {
        this.board = board;
        this.imgUrl = imgUrl;
    }

    public BoardImgCommonRequestDto(BoardImgUrls boardImgUrls) {
        this.imgUrl = boardImgUrls.getImgUrl();
    }

    public BoardImgUrls toEntity() {
        return BoardImgUrls.builder()
                .board(board)
                .imgUrl(imgUrl)
                .build();
    }
}
