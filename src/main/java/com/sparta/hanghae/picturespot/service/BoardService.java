package com.sparta.hanghae.picturespot.service;

import com.sparta.hanghae.picturespot.dto.request.board.BoardSaveRequestDto;
import com.sparta.hanghae.picturespot.dto.response.board.BoardSaveResponseDto;
import com.sparta.hanghae.picturespot.model.Board;
import com.sparta.hanghae.picturespot.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public BoardSaveResponseDto save(BoardSaveRequestDto requestDto) {
        Board boardEntity = boardRepository.save(requestDto.toEntity());

        return new BoardSaveResponseDto(boardEntity);
    }
}
