package com.sparta.hanghae.picturespot.dto.response.board;

import com.sparta.hanghae.picturespot.dto.request.img.BoardImgCommonRequestDto;
import com.sparta.hanghae.picturespot.model.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class BoardPageResponseDto {
    private Long boardId;
    private LocalDateTime modified;
    private Long writerId;
    private String writerName;
    private String writerImgUrl;
    private String title;
    private String content;
    private String spotName;
    private boolean liked;
    private int likeCount;
    private String category;
    private List<BoardImgCommonRequestDto> boardImgReponseDtoList = new ArrayList<>();
    private List<BoardDetailCommentsDto> boardDetailCommentDtoList = new ArrayList<>();

    public BoardPageResponseDto(Board boardEntity, boolean likeCheck, int likeCount, List<BoardDetailCommentsDto> boardDetailCommentsDtoList, List<BoardImgCommonRequestDto> reponseDto) {
        this.boardId = boardEntity.getId();
        this.modified = boardEntity.getModified();
        this.writerId = boardEntity.getUser().getId();
        this.writerName = boardEntity.getUser().getNickname();
        this.writerImgUrl = boardEntity.getUser().getImgUrl();
        this.title = boardEntity.getTitle();
        this.content = boardEntity.getContent();
        this.spotName = boardEntity.getSpotName();
        this.liked = likeCheck;
        this.likeCount = likeCount;
        this.category = boardEntity.getCategory();
        this.boardImgReponseDtoList = reponseDto;
        this.boardDetailCommentDtoList = boardDetailCommentsDtoList;
    }
}
