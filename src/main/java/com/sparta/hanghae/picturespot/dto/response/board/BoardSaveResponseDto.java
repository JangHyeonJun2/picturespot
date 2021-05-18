package com.sparta.hanghae.picturespot.dto.response.board;

import com.sparta.hanghae.picturespot.dto.request.img.BoardImgCommonRequestDto;
import com.sparta.hanghae.picturespot.dto.request.img.BoardImgSaveRequestDto;
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
    private Long boardId;
    private String title;
    private String content;
    private String category;
    private double latitude;
    private double longitude;
    private String spotName;
    private List<BoardImgSaveRequestDto> boardImgReponseDtoList = new ArrayList<>();
//    private Long userId;
//    private String nickName;


    public BoardSaveResponseDto(Board entity, List<BoardImgSaveRequestDto> reponseDto) {
        this.boardId = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.category = entity.getCategory();
        this.latitude = entity.getLatitude();
        this.longitude = entity.getLongitude();
        this.boardImgReponseDtoList = reponseDto;
        this.spotName = entity.getSpotName();
//        this.userId = entity.getUser().getId();
//        this.nickName = entity.getUser().getNickname();

    }
}
