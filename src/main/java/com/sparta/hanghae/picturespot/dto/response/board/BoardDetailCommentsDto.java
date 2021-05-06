package com.sparta.hanghae.picturespot.dto.response.board;

import com.sparta.hanghae.picturespot.model.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardDetailCommentsDto {
    private Long commentId;
    private LocalDateTime modified;
    private Long userId;
    private String writerName;
    private String writerImgUrl;
    private String content;

    @Builder
    public BoardDetailCommentsDto(Comment commentEntity) {
        this.commentId = commentEntity.getId();
        this.modified = commentEntity.getModified();
        this.userId = commentEntity.getUser().getId();
        this.writerName = commentEntity.getUser().getNickname();
        this.writerImgUrl = commentEntity.getUser().getImgUrl();
        this.content = commentEntity.getContent();
    }
}
