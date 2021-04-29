package com.sparta.hanghae.picturespot.dto.requestDto;

import com.sparta.hanghae.picturespot.model.Board;
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
    private BigDecimal latitude;
    private BigDecimal logitude;
    private String imgUrl;

    @Builder
    public BoardSaveRequestDto(String title, String content, String category, BigDecimal latitude, BigDecimal logitude, String imgUrl) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.latitude = latitude;
        this.logitude = logitude;
        this.imgUrl = imgUrl;
    }

    public Board toEntity() {
        return Board.builder()
                .title(title)
                .content(content)
                .category(category)
                .latitude(latitude)
                .longitude(logitude)
                .imgUrl(imgUrl)
                .build();
    }
}
