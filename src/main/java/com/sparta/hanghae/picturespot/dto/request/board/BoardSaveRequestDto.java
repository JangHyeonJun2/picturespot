package com.sparta.hanghae.picturespot.dto.request.board;

import com.sparta.hanghae.picturespot.model.Board;
import com.sparta.hanghae.picturespot.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class BoardSaveRequestDto {
    private String title;
    private String content;
    private String category;
    private double latitude;
    private double logitude;
//    private String[] imgUrls;
    private String spotName;
    private User user;

    @Builder
    public BoardSaveRequestDto(String title, String content, String category, double latitude, double logitude, String spotName, User user) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.latitude = latitude;
        this.logitude = logitude;
        //배열 사이즈 초기화 및 값 설정.
//        this.imgUrls = new String[imgUrls.length];
//        for (int i=0; i<imgUrls.length; i++) {
//            this.imgUrls[i] = imgUrls[i];
//        }
        this.spotName = spotName;
        this.user = user;
    }

    public Board toEntity() {
        return Board.builder()
                .title(title)
                .content(content)
                .category(category)
                .latitude(latitude)
                .longitude(logitude)
                .spotName(spotName)
                .user(user)
                .build();
    }
}
