package com.sparta.hanghae.picturespot.dto.response.mypage;

import com.sparta.hanghae.picturespot.dto.request.img.BoardImgCommonRequestDto;
import com.sparta.hanghae.picturespot.model.Board;
import com.sparta.hanghae.picturespot.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor
public class MypageResponseDto {

    //board
    private Long boardId;
    private double latitude;
    private double longitude;
    private String spotName;
    private String category;
    private List<BoardImgCommonRequestDto> boardImgResponseDtoList = new ArrayList<>();

    //heart
    private boolean liked;
    private int likeCount;

    @Builder
    public MypageResponseDto(Board boardEntity, boolean likeCheck, int likeCount, List<BoardImgCommonRequestDto> responseDto) {

        //board 정보
        this.boardId = boardEntity.getId();
        this.category = boardEntity.getCategory();
        this.latitude = boardEntity.getLatitude();
        this.longitude = boardEntity.getLongitude();
        this.spotName = boardEntity.getSpotName();

        //이미지
        this.boardImgResponseDtoList = responseDto;

        //좋아요
        this.liked = likeCheck;
        this.likeCount = likeCount;

        }
    }

