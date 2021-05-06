package com.sparta.hanghae.picturespot.dto.response.board;

import com.sparta.hanghae.picturespot.dto.request.img.BoardImgCommonRequestDto;
import com.sparta.hanghae.picturespot.model.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class LoadingBoardMapResponseDto {
    private Long boardId;
    private String title;
    private String content;
    private String[] imgUrls;
    private boolean liked;
    private int likeCount;
    private String writerName;
    private String writerImgUrl;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String spotName;
    private String category;
    private List<BoardDetailCommentsDto> boardDetailCommentDtoList = new ArrayList<>();
    private List<BoardImgCommonRequestDto> boardImgReponseDtoList = new ArrayList<>();

    @Builder
    public LoadingBoardMapResponseDto(Board boardEntity, boolean liked, int likeCount, List<BoardDetailCommentsDto> detailCommentsDtos, List<BoardImgCommonRequestDto> reponseDto) {
        this.boardId = boardEntity.getId();
        this.title = boardEntity.getTitle();
        this.content = boardEntity.getContent();
        //게시물 배열 초기화
//        this.imgUrls = new String[boardEntity.getImgUrls().length];
//        for (int i=0; i<boardEntity.getImgUrls().length; i++) {
//            this.imgUrls[i] = boardEntity.getImgUrls()[i];
//        }
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
}
