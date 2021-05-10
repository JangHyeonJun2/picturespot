package com.sparta.hanghae.picturespot.dto.response.comment;

import com.sparta.hanghae.picturespot.model.Board;
import com.sparta.hanghae.picturespot.model.Comment;
import com.sparta.hanghae.picturespot.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentSaveResponseDto {
    private Long commentId;
    private String content;
    //이거를 왜 주석처리 했냐면 양방향을 board와 comment를 걸어놨는데 dto에 객체를 JSON으로 보내주게 되면 무한루프가 걸리게 되서 주석 처리함.
//    private User user;
//    private Board board;
    private Long userId;
    private String nickName;
    private String userImgUrl;
    private Long boardId;
    private LocalDateTime modified;

    public CommentSaveResponseDto(Comment entity) {
        this.commentId = entity.getId();
        this.content = entity.getContent();
//        this.user = entity.getUser();
//        this.board = entity.getBoard();
        this.userId = entity.getUser().getId();
        this.nickName = entity.getUser().getNickname();
        this.userImgUrl = entity.getUser().getImgUrl();
        this.boardId = entity.getBoard().getId();
        this.modified = entity.getModified();
    }
}


