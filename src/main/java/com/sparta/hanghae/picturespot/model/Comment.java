package com.sparta.hanghae.picturespot.model;

import com.sparta.hanghae.picturespot.dto.request.comment.CommentUpdateRequestDto;
import com.sparta.hanghae.picturespot.dto.response.board.BoardDetailCommentsDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Entity
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_ID")
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    private Board board;



    @Builder
    public Comment(String content, User user, Board board) {
        this.content = content;
        this.user = user;
        this.board = board;
    }

    public Comment update(CommentUpdateRequestDto requestDto) {
        this.content = requestDto.getContent();
        return this;
    }

    public static Set<BoardDetailCommentsDto> toDtoList(Set<Comment> dtos) {
        Set<BoardDetailCommentsDto> detailCommentsDtos = new HashSet<>();
        for (Comment comment : dtos) {
            detailCommentsDtos.add(new BoardDetailCommentsDto(comment));
        }
        return detailCommentsDtos;
    }
}
