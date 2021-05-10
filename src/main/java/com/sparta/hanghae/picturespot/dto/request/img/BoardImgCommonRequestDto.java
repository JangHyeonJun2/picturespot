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
    private Long imgUrlId;
    private String imgUrl;

    public BoardImgCommonRequestDto(BoardImgUrls boardImgUrls) {
        this.imgUrlId = boardImgUrls.getId();
        this.imgUrl = boardImgUrls.getImgUrl();
    }
}
