package com.sparta.hanghae.picturespot.dto.response.board;

import com.sparta.hanghae.picturespot.dto.request.img.BoardImgCommonRequestDto;
import com.sparta.hanghae.picturespot.model.Board;
import com.sparta.hanghae.picturespot.model.Comment;
import com.sparta.hanghae.picturespot.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class BoardsGetResponseDto {
    private Long boardId;
    private Long writerId;
    private String writerName;
    private String writerImgUrl;
    private String title;
    private String content;
    private boolean liked;
    private int likeCount;
    private String category;
    private List<BoardImgCommonRequestDto> boardImgReponseDtoList = new ArrayList<>();


    @Builder
    public BoardsGetResponseDto(User userEntity, Board boardEntity, boolean likeCheck, int likeCount, List<BoardImgCommonRequestDto> reponseDto) {
        //writer의 정보 초기화
        this.boardId = boardEntity.getId();
        this.writerId = userEntity.getId();
        this.writerName = userEntity.getNickname();
        this.writerImgUrl = userEntity.getImgUrl();
        //board 정보 초기화
        this.title = boardEntity.getTitle();
        this.content = boardEntity.getContent();
        this.category = boardEntity.getCategory();
        //게시물 배열 초기화
//        this.imgUrls = new String[boardEntity.getImgUrls().length];
//        for (int i=0; i<boardEntity.getImgUrls().length; i++) {
//            this.imgUrls[i] = boardEntity.getImgUrls()[i];
//        }
        this.liked = likeCheck;
        this.likeCount = likeCount;
        this.boardImgReponseDtoList = reponseDto;
    }


}
