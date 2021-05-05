package com.sparta.hanghae.picturespot.service;

import com.sparta.hanghae.picturespot.dto.request.board.BoardSaveRequestDto;
import com.sparta.hanghae.picturespot.dto.response.board.*;
import com.sparta.hanghae.picturespot.model.Board;
import com.sparta.hanghae.picturespot.model.Comment;
import com.sparta.hanghae.picturespot.model.Heart;
import com.sparta.hanghae.picturespot.model.User;
import com.sparta.hanghae.picturespot.repository.BoardRepository;
import com.sparta.hanghae.picturespot.repository.CommentRepository;
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
    private final CommentRepository commentRepository;

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

    //검색 게시물 조회
    public List<BoardGetSearchResponseDto> search(String searchText, User loginUser) {
        List<BoardGetSearchResponseDto> searchResponseDtos = new ArrayList<>();

//        searchText = "%" + searchText + "%";
        List<Board> findSearchBoardList = boardRepository.findByTitleContainingOrContentContainingOrderByModifiedDesc(searchText, searchText); //OrderByModifiedDesc
        boolean likeCheck = true;
        for (int i=0; i<findSearchBoardList.size(); i++) {
            if (loginUser == null) {
                likeCheck = false;
            } else
                likeCheck = heartRepository.existsByBoardIdAndUserId(findSearchBoardList.get(i).getId(), loginUser.getId());
            //게시물에 대한 좋아요 개수
            List<Heart> allByBoardId = heartRepository.findAllByBoardId(findSearchBoardList.get(i).getId());

            BoardGetSearchResponseDto responseDto = new BoardGetSearchResponseDto(findSearchBoardList.get(i), likeCheck, allByBoardId.size());
            searchResponseDtos.add(responseDto);
        }
        return searchResponseDtos;
    }
    //게시물 상세보기
    public BoardDetailResponseDto detail(Long boardId, User loginUser) {
        List<BoardDetailCommentsDto> detailCommentsDtoList = new ArrayList<>();
        boolean likeCheck = true;

        Board findBoard = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다."));
        List<Comment> allByBoardId = commentRepository.findAllByBoardId(findBoard.getId());
        for (int i=0; i<allByBoardId.size(); i++) {
            BoardDetailCommentsDto tempDto = new BoardDetailCommentsDto(allByBoardId.get(i));
            detailCommentsDtoList.add(tempDto);
        }
        if (loginUser == null) {
            likeCheck = false;
        } else {
            likeCheck = heartRepository.existsByBoardIdAndUserId(findBoard.getId(), loginUser.getId());
        }

        //게시물에 대한 좋아요 개수
        List<Heart> allBoardHeartCount = heartRepository.findAllByBoardId(findBoard.getId());

        return new BoardDetailResponseDto(findBoard,likeCheck,allBoardHeartCount.size(),detailCommentsDtoList);
    }
    //게시물 메인페이지(지도) 로딩될 때 데이터 보내주기
    public List<LoadingBoardMapResponseDto>loadingMapBoard(User loginUser) {
        List<Board> boards = boardRepository.findAll();
        List<LoadingBoardMapResponseDto> loadingBoardMapResponseDtos = new ArrayList<>();


        boolean likeCheck = true;

        for (int i=0; i<boards.size(); i++) {
            List<Comment> comments = commentRepository.findAllByBoardId(boards.get(i).getId());
            List<BoardDetailCommentsDto> detailCommentsDtoList = new ArrayList<>();
            for (int j=0; j<comments.size(); j++) {
                BoardDetailCommentsDto commentsDto = new BoardDetailCommentsDto(comments.get(j));
                detailCommentsDtoList.add(commentsDto);
            }
            if (loginUser == null) {
                likeCheck = false;
            } else {
                likeCheck = heartRepository.existsByBoardIdAndUserId(boards.get(i).getId(), loginUser.getId());
            }
            List<Heart> allByBoardId = heartRepository.findAllByBoardId(boards.get(i).getId());
            LoadingBoardMapResponseDto boardMapResponseDto = new LoadingBoardMapResponseDto(boards.get(i), likeCheck, allByBoardId.size(), detailCommentsDtoList);
            loadingBoardMapResponseDtos.add(boardMapResponseDto);
        }
        return loadingBoardMapResponseDtos;
    }
}
