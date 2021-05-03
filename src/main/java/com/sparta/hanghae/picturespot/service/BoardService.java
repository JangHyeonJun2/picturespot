package com.sparta.hanghae.picturespot.service;

import com.sparta.hanghae.picturespot.dto.request.board.BoardSaveRequestDto;
import com.sparta.hanghae.picturespot.dto.response.board.BoardGetResponseDto;
import com.sparta.hanghae.picturespot.dto.response.board.BoardsGetResponseDto;
import com.sparta.hanghae.picturespot.dto.response.board.BoardSaveResponseDto;
import com.sparta.hanghae.picturespot.model.Board;
import com.sparta.hanghae.picturespot.model.User;
import com.sparta.hanghae.picturespot.repository.BoardRepository;
import com.sparta.hanghae.picturespot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    //게시물 저장
    @Transactional
    public BoardSaveResponseDto save(BoardSaveRequestDto requestDto) {
        Board boardEntity = boardRepository.save(requestDto.toEntity());

        return new BoardSaveResponseDto(boardEntity);
    }
    //게시물 삭제
    @Transactional
    public Long delete(Long boardId, Long userId) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("게시물이 존재 하지 않습니다."));
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("계정이 존재하지 않습니다."));

        if (board.getUser().getId().equals(userId)) {
            boardRepository.deleteById(boardId);
            return board.getId();//삭제된 게시물 id 리턴.
        }
        else{
            return null;
        }
    }

    //커뮤니티 게시글 조회
    public List<BoardsGetResponseDto> getBoards() {
        List<BoardsGetResponseDto> boardGetResponseDtoList = new ArrayList<>();
        List<User> userList = userRepository.findAll();

        for (int i=0; i<userList.size(); i++) {
            User user = userList.get(i);
            BoardsGetResponseDto brdto = new BoardsGetResponseDto(user);
            List<Board> boardList = boardRepository.findAllByUserId(user.getId());
            for (int j=0; j<boardList.size(); j++) {
                BoardGetResponseDto boardGetResponseDto = new BoardGetResponseDto(boardList.get(i));
                brdto.getBoardGetResponseDtos().add(boardGetResponseDto);
            }
            boardGetResponseDtoList.add(brdto);
        }

        return boardGetResponseDtoList;
    }
}
