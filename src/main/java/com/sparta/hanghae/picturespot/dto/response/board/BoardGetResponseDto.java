package com.sparta.hanghae.picturespot.dto.response.board;

import com.sparta.hanghae.picturespot.model.Board;
import com.sparta.hanghae.picturespot.model.Heart;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardGetResponseDto {
    private String title;
    private String content;
    private boolean liked;
    private int likeCount;
    private String[] imgUrls;
    private String category;

    @Builder
    public BoardGetResponseDto(Board entity) {
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.category = entity.getCategory();
        //배열 사이즈 초기화 및 값 설정.
        this.imgUrls = new String[entity.getImgUrls().length];
        String[] imgUrlArr = entity.getImgUrls();
        for (int i=0; i<entity.getImgUrls().length; i++) {
            this.imgUrls[i] = imgUrlArr[i];
        }
    }

//    public void inputBoardInfo(Board board) {
//        this.title = board.getTitle();
//        this.content = board.getContent();
//        this.category = board.getCategory();
//        //배열 사이즈 초기화 및 값 설정.
//        this.imgUrls = new String[board.getImgUrls().length];
//        String[] imgUrlArr = board.getImgUrls();
//        for (int i=0; i<board.getImgUrls().length; i++) {
//            this.imgUrls[i] = imgUrlArr[i];
//        }
//    }

    public void inputHeartInfo(Heart heart) {
        this.liked = heart.isLiked();
        this.likeCount = heart.getLikeCount();
    }
}
