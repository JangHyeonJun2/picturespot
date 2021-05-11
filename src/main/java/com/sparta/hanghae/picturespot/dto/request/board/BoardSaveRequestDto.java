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
    private double longitude;
    private String spotName;
    private User user;

    @Builder
    public BoardSaveRequestDto(String title, String content, String category, double latitude, double longitude, String spotName, User user) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.latitude = latitude;
        this.longitude = longitude;
        this.spotName = spotName;
        this.user = user;
    }

    public Board toEntity() {
        return Board.builder()
                .title(title)
                .content(content)
                .category(category)
                .latitude(latitude)
                .longitude(longitude)
                .spotName(spotName)
                .user(user)
                .build();
    }
}
