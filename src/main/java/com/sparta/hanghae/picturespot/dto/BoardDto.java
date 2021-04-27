package com.sparta.hanghae.picturespot.dto;

import com.sparta.hanghae.picturespot.model.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class BoardDto {

    //private Long id;
    private String title;
    private String content;
    private String category;
    private String imgUrl;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private int likeCount;
    private Long userId;


    public BoardDto(Board board){
        //this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.category = board.getCategory();
        this.imgUrl = board.getImgUrl();
        this.latitude = board.getLatitude();
        this.longitude = board.getLongitude();
        this.likeCount = board.getLikeCount();
        this.userId = board.getUser().getId();
    }

}
