package com.sparta.hanghae.picturespot.dto.response.board;

import com.sparta.hanghae.picturespot.model.Board;
import com.sparta.hanghae.picturespot.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class BoardSaveResponseDto {
    private String title;
    private String content;
    private String category;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String spotName;
    private String[] imgUrls;
    private User user;

    public BoardSaveResponseDto(Board entity) {
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.category = entity.getCategory();
        this.latitude = entity.getLatitude();
        this.longitude = entity.getLongitude();
        this.imgUrls = entity.getImgUrls();
        this.spotName = entity.getSpotName();
        this.user = entity.getUser();
    }
}
