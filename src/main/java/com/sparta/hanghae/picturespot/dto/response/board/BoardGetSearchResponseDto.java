package com.sparta.hanghae.picturespot.dto.response.board;

import com.sparta.hanghae.picturespot.dto.request.img.BoardImgCommonRequestDto;
import com.sparta.hanghae.picturespot.model.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class BoardGetSearchResponseDto {
    private Long boardId;
    private String writerName;
    private String writerImgUrl;
    private String title;
    private String content;
    private String category;
    private boolean likeCheck;
    private int likeCount;
    private List<BoardImgCommonRequestDto> requestDto = new ArrayList<>();

    @Builder
    public BoardGetSearchResponseDto(Board boardEntity, boolean likeCheck, int likeCount, List<BoardImgCommonRequestDto> requestDto) {
        this.boardId = boardEntity.getId();
        this.writerName = boardEntity.getUser().getNickname();
        this.writerImgUrl = boardEntity.getUser().getImgUrl();
        this.title = boardEntity.getTitle();
        this.content = boardEntity.getContent();
        //게시물 배열 초기화
//        this.imgUrls = new String[boardEntity.getImgUrls().length];
//        for (int i=0; i<boardEntity.getImgUrls().length; i++) {
//            this.imgUrls[i] = boardEntity.getImgUrls()[i];
//        }
        this.category = boardEntity.getCategory();
        this.likeCheck = likeCheck;
        this.likeCount = likeCount;
        this.requestDto = requestDto;
    }
}
