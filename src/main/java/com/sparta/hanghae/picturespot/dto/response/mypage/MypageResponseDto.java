package com.sparta.hanghae.picturespot.dto.response.mypage;

import com.sparta.hanghae.picturespot.dto.request.img.BoardImgCommonRequestDto;
import com.sparta.hanghae.picturespot.model.Board;
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
    private String writer;
    private String title;
    private String content;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String category;
    private String spotName;
    private LocalDateTime modified;

    //heart
    private boolean liked;
    private int likeCount;

    //comments
    private List<MypageCommentResponseDto> comments;
    private List<BoardImgCommonRequestDto> boardImgReponseDtoList = new ArrayList<>();


    @Builder
    public MypageResponseDto(Board boardEntity, List<MypageCommentResponseDto> comments, boolean likeCheck, int likeCount, List<BoardImgCommonRequestDto> reponseDto) {

        //board 정보
        this.boardId = boardEntity.getId();
        this.writer = boardEntity.getUser().getNickname();
        this.title = boardEntity.getTitle();
        this.content = boardEntity.getContent();
        this.category = boardEntity.getCategory();
        this.latitude = boardEntity.getLatitude();
        this.longitude = boardEntity.getLongitude();
        this.spotName = boardEntity.getSpotName();
        this.modified = boardEntity.getModified();
        //이미지
        this.boardImgReponseDtoList = reponseDto;

        //좋아요
        this.liked = likeCheck;
        this.likeCount = likeCount;

        //댓글
        this.comments = comments;
        }
    }

