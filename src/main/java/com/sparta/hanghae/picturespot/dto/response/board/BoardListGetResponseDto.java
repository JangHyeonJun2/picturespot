package com.sparta.hanghae.picturespot.dto.response.board;

import com.sparta.hanghae.picturespot.model.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardListGetResponseDto {
    private Long boardId;
    private LocalDateTime modified;
    private String spotName;
    private boolean liked;
    private int likeCount;
    private String category;
    private String boardImgUrl;

    public BoardListGetResponseDto(Board boardEntity, boolean liked, int likeCount) {
        this.boardId = boardEntity.getId();
        this.modified = boardEntity.getModified();
        this.spotName = boardEntity.getSpotName();
        this.liked = liked;
        this.likeCount = likeCount;
        this.category = boardEntity.getCategory();
        this.boardImgUrl = boardEntity.getBoardImgUrls().iterator().next().getImgUrl();
    }
}
