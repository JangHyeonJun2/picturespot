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
public class BoardDetailResponseDto {
    private Long boardId; //게시글 아이디
    private Long userId; //게시글 작성자 아이디
    private String writerName;
    private String writerImgUrl;
    private String title;
    private String content;
    private boolean liked;
    private int likeCount;
    private String spotName;
    private List<BoardDetailCommentsDto> boardDetailCommentDtoList = new ArrayList<>();
    private List<BoardImgCommonRequestDto> boardImgReponseDtoList = new ArrayList<>();

    @Builder
    public BoardDetailResponseDto(Board boardEntity, boolean likeCheck, int likeCount, List<BoardDetailCommentsDto> boardDetailCommentsDtoList, List<BoardImgCommonRequestDto> requestDto) {
        this.boardId = boardEntity.getId();
        this.userId = boardEntity.getUser().getId();
        this.writerName = boardEntity.getUser().getNickname();
        this.writerImgUrl = boardEntity.getUser().getImgUrl();
        this.title = boardEntity.getTitle();
        this.content = boardEntity.getContent();
        //게시물 배열 초기화
//        this.imgUrls = new String[boardEntity.getImgUrls().length];
//        for (int i=0; i<boardEntity.getImgUrls().length; i++) {
//            this.imgUrls[i] = boardEntity.getImgUrls()[i];
//        }
        this.spotName = boardEntity.getSpotName();
        this.liked = likeCheck;
        this.likeCount = likeCount;
        this.boardDetailCommentDtoList = boardDetailCommentsDtoList;
        this.boardImgReponseDtoList = requestDto;
    }
}
