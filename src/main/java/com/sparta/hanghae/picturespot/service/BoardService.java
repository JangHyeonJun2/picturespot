package com.sparta.hanghae.picturespot.service;

import com.sparta.hanghae.picturespot.dto.requestDto.BoardSaveRequestDto;
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
    public Board save(BoardSaveRequestDto requestDto) {
        return boardRepository.save(requestDto.toEntity());
    }
}
