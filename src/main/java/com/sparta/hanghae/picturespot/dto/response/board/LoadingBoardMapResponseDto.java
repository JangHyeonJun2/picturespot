package com.sparta.hanghae.picturespot.dto.response.board;

import com.sparta.hanghae.picturespot.dto.request.img.BoardImgCommonRequestDto;
import com.sparta.hanghae.picturespot.model.Board;
import com.sparta.hanghae.picturespot.model.BoardImgUrls;
import com.sparta.hanghae.picturespot.model.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
public class LoadingBoardMapResponseDto {
    private Long boardId;
    private double latitude;
    private double longitude;
    private String spotName;
    private String category;
    private Set<BoardImgCommonRequestDto> boardImgReponseDtoList = new HashSet<>();


    public LoadingBoardMapResponseDto(Board boardEntity, Set<BoardImgCommonRequestDto> reponseDto2) {
        this.boardId = boardEntity.getId();
        this.latitude = boardEntity.getLatitude();
        this.longitude = boardEntity.getLongitude();
        this.spotName = boardEntity.getSpotName();
        this.category = boardEntity.getCategory();
        this.boardImgReponseDtoList = reponseDto2;
    }

}
