package com.sparta.hanghae.picturespot.service;

import com.sparta.hanghae.picturespot.dto.request.board.BoardSaveRequestDto;
import com.sparta.hanghae.picturespot.dto.response.board.BoardsGetResponseDto;
import com.sparta.hanghae.picturespot.dto.response.board.BoardSaveResponseDto;
import com.sparta.hanghae.picturespot.model.Board;
import com.sparta.hanghae.picturespot.model.Heart;
import com.sparta.hanghae.picturespot.model.User;
import com.sparta.hanghae.picturespot.repository.BoardRepository;
import com.sparta.hanghae.picturespot.repository.HeartRepository;
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
    private final HeartRepository heartRepository;

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
    public List<BoardsGetResponseDto> getBoards(User loginUser) {
        List<BoardsGetResponseDto> boardGetResponseDtoList = new ArrayList<>();
        List<Board> boardAll = boardRepository.findAll();
        boolean likeCheck = true;
        for (int i=0; i<boardAll.size(); i++) {
            //로그인 사용자가 게시물을 좋아요 했는지 안했는지 체크!
            if (loginUser == null) {//로그인이 되어있지 않은 사용자일 때
                likeCheck = false;
            } else //로그인이 되어있는 사용자 일 때
                likeCheck = heartRepository.existsByBoardIdAndUserId(boardAll.get(i).getId(), loginUser.getId());
            //게시물에 대한 좋아요 개수
            List<Heart> allByBoardId = heartRepository.findAllByBoardId(boardAll.get(i).getId());

            BoardsGetResponseDto brdto = new BoardsGetResponseDto(boardAll.get(i).getUser(), boardAll.get(i), likeCheck, allByBoardId.size());

            boardGetResponseDtoList.add(brdto);
        }

        return boardGetResponseDtoList;
    }
}
