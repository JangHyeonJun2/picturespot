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
    private String title;
    private String content;
    private boolean liked;
    private int likeCount;
    private String writerName;
    private String writerImgUrl;
    private double latitude;
    private double longitude;
    private String spotName;
    private String category;
    private List<BoardDetailCommentsDto> boardDetailCommentDtoList = new ArrayList<>();
    private List<BoardImgCommonRequestDto> boardImgReponseDtoList = new ArrayList<>();

    private Set<BoardDetailCommentsDto> boardDetailCommentDtoList2 = new HashSet<>();
    private Set<BoardImgCommonRequestDto> boardImgReponseDtoList2 = new HashSet<>();

    @Builder
    public LoadingBoardMapResponseDto(Board boardEntity, boolean liked, int likeCount, List<BoardDetailCommentsDto> detailCommentsDtos, List<BoardImgCommonRequestDto> reponseDto) {
        this.boardId = boardEntity.getId();
        this.title = boardEntity.getTitle();
        this.content = boardEntity.getContent();
        this.liked = liked;
        this.likeCount = likeCount;
        this.writerName = boardEntity.getUser().getNickname();
        this.writerImgUrl = boardEntity.getUser().getImgUrl();
        this.latitude = boardEntity.getLatitude();
        this.longitude = boardEntity.getLongitude();
        this.spotName = boardEntity.getSpotName();
        this.category = boardEntity.getCategory();
        this.boardDetailCommentDtoList = detailCommentsDtos;
        this.boardImgReponseDtoList = reponseDto;
    }
    public LoadingBoardMapResponseDto(Board boardEntity, boolean liked, int likeCount, Set<BoardDetailCommentsDto> detailCommentsDtos2, Set<BoardImgCommonRequestDto> reponseDto2) {
        this.boardId = boardEntity.getId();
        this.title = boardEntity.getTitle();
        this.content = boardEntity.getContent();
        this.liked = liked;
        this.likeCount = likeCount;
        this.writerName = boardEntity.getUser().getNickname();
        this.writerImgUrl = boardEntity.getUser().getImgUrl();
        this.latitude = boardEntity.getLatitude();
        this.longitude = boardEntity.getLongitude();
        this.spotName = boardEntity.getSpotName();
        this.category = boardEntity.getCategory();
        this.boardDetailCommentDtoList2 = detailCommentsDtos2;
        this.boardImgReponseDtoList2 = reponseDto2;
    }

}
