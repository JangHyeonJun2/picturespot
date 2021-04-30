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
    private BigDecimal logitude;
    private String[] imgUrls;
    private User user;

    public BoardSaveResponseDto(Board entity) {
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.category = entity.getCategory();
        this.latitude = entity.getLatitude();
        this.logitude = entity.getLatitude();
        this.imgUrls = entity.getImgUrls();
        this.user = entity.getUser();
    }
}
