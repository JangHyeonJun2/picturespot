package com.sparta.hanghae.picturespot.dto.request.board;

import com.sparta.hanghae.picturespot.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardUpdateRequestDto {
    private Long boardId;
    private String title;
    private String content;
//    private Long[] deleteImgUrls;
//    private String[] imgUrls;

    public BoardUpdateRequestDto(Long boardId, String title, String content) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;

//        this.deleteImgUrls = new Long[deleteImgUrls.length];
//        for (int i=0; i<deleteImgUrls.length; i++) {
//            this.deleteImgUrls[i] = deleteImgUrls[i];
//        }
//
//        this.imgUrls = new String[imgUrls.length];
//        for (int i=0; i<imgUrls.length; i++) {
//            this.imgUrls[i] = imgUrls[i];
//        }
    }
}
