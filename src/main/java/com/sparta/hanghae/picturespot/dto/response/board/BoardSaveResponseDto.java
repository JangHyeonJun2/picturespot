package com.sparta.hanghae.picturespot.dto.response.board;

import com.sparta.hanghae.picturespot.dto.request.img.BoardImgCommonRequestDto;
import com.sparta.hanghae.picturespot.model.Board;
import com.sparta.hanghae.picturespot.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class BoardSaveResponseDto {
    private String title;
    private String content;
    private String category;
    private BigDecimal latitude;
    private BigDecimal logitude;
    private String spotName;
    private List<BoardImgCommonRequestDto> boardImgReponseDtoList = new ArrayList<>();
    private User user;

    public BoardSaveResponseDto(Board entity, List<BoardImgCommonRequestDto> reponseDto) {
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.category = entity.getCategory();
        this.latitude = entity.getLatitude();
        this.logitude = entity.getLatitude();
        this.boardImgReponseDtoList = reponseDto;
        this.spotName = entity.getSpotName();
        this.user = entity.getUser();
    }
}
