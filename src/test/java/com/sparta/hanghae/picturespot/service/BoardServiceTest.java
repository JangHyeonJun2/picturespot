package com.sparta.hanghae.picturespot.service;

import com.sparta.hanghae.picturespot.dto.request.img.BoardImgCommonRequestDto;
import com.sparta.hanghae.picturespot.dto.response.board.BoardDetailCommentsDto;
import com.sparta.hanghae.picturespot.dto.response.board.LoadingBoardMapResponseDto;
import com.sparta.hanghae.picturespot.model.Board;
import com.sparta.hanghae.picturespot.model.BoardImgUrls;
import com.sparta.hanghae.picturespot.model.Comment;
import com.sparta.hanghae.picturespot.repository.BoardRepository;
import com.sparta.hanghae.picturespot.repository.HeartRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class BoardServiceTest {
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    HeartRepository heartRepository;

    @Test
    public void 맵로딩될때패치조인으로_한번에_데이터받기() {
        List<LoadingBoardMapResponseDto> responseDtos = new ArrayList<>();
        List<Board> boards = boardRepository.findAllFetchJoin();
        for (Board board : boards) {
            Set<BoardDetailCommentsDto> detailCommentsDtos = Comment.toDtoList(board.getComments());
            Set<BoardImgCommonRequestDto> imgCommonRequestDtos = BoardImgUrls.toDtoList(board.getBoardImgUrls());
            responseDtos.add(new LoadingBoardMapResponseDto(board, false, board.getHearts().size(), detailCommentsDtos, imgCommonRequestDtos));
        }

        Assertions.assertThat(33).isEqualTo(responseDtos.size());
    }
}