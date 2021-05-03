package com.sparta.hanghae.picturespot.dto.response.board;

import com.sparta.hanghae.picturespot.model.Board;
import com.sparta.hanghae.picturespot.model.Heart;
import com.sparta.hanghae.picturespot.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class BoardsGetResponseDto {
    private Long writerId;
    private String writerName;
    private String writerImgUrl;
    private List<BoardGetResponseDto> boardGetResponseDtos = new ArrayList<>();

    @Builder
    public BoardsGetResponseDto(Long writerId, String writerName, String writerImgUrl, List<BoardGetResponseDto> boardGetResponseDtos) {
        this.writerId = writerId;
        this.writerName = writerName;
        this.writerImgUrl = writerImgUrl;
        for (int i=0; i<boardGetResponseDtos.size(); i++) {
            this.boardGetResponseDtos.add(boardGetResponseDtos.get(i));
        }

    }

    public BoardsGetResponseDto(User entity) {
        //user 관련 정보
        this.writerId = entity.getId();
        this.writerName = entity.getNickname();
        this.writerImgUrl = entity.getImgUrl();
    }
}
