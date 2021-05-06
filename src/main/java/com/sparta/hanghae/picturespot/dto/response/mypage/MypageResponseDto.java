package com.sparta.hanghae.picturespot.dto.response.mypage;

import com.sparta.hanghae.picturespot.dto.request.img.BoardImgCommonRequestDto;
import com.sparta.hanghae.picturespot.model.Board;
import com.sparta.hanghae.picturespot.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor
public class MypageResponseDto {

    //user
    private String nickname;
    private String userImgUrl;
    private String userMsg;

    //board
    private Long boardId;
    private String writer;
    private String title;
    private String content;
//    private String[] imgUrls;
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
    public MypageResponseDto(User userEntity, Board boardEntity, List<MypageCommentResponseDto> comments, boolean likeCheck, int likeCount, List<BoardImgCommonRequestDto> reponseDto) {
        //user정보
        this.nickname = userEntity.getNickname();
        this.userImgUrl = userEntity.getImgUrl();
        this.userMsg = userEntity.getIntroduceMsg();

        //board 정보
        this.boardId = boardEntity.getId();
        this.writer = boardEntity.getUser().getNickname();
        this.title = boardEntity.getTitle();
        this.content = boardEntity.getContent();
        this.category = boardEntity.getCategory();
        this.spotName = boardEntity.getSpotName();
        this.modified = boardEntity.getModified();
        //게시물 배열 초기화
//        this.imgUrls = new String[boardEntity.getImgUrls().length];
//        for (int i=0; i<boardEntity.getImgUrls().length; i++) {
//            this.imgUrls[i] = boardEntity.getImgUrls()[i];
//        }

        //좋아요
        this.liked = likeCheck;
        this.likeCount = likeCount;

        //댓글
        this.comments = comments;
        //이미지
        this.boardImgReponseDtoList = reponseDto;
    }
}
