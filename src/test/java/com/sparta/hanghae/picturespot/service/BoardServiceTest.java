//package com.sparta.hanghae.picturespot.service;
//
//import com.sparta.hanghae.picturespot.dto.request.img.BoardImgCommonRequestDto;
//import com.sparta.hanghae.picturespot.dto.response.board.BoardDetailCommentsDto;
//import com.sparta.hanghae.picturespot.dto.response.board.LoadingBoardMapResponseDto;
//import com.sparta.hanghae.picturespot.model.Board;
//import com.sparta.hanghae.picturespot.model.BoardImgUrls;
//import com.sparta.hanghae.picturespot.model.Comment;
//import com.sparta.hanghae.picturespot.model.Timestamped;
//import com.sparta.hanghae.picturespot.repository.BoardImgUrlsRepository;
//import com.sparta.hanghae.picturespot.repository.BoardRepository;
//import com.sparta.hanghae.picturespot.repository.HeartRepository;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.*;
//import java.util.stream.Collectors;
//
//import static org.junit.jupiter.api.Assertions.*;
//@SpringBootTest
//class BoardServiceTest {
//    @Autowired
//    BoardRepository boardRepository;
//    @Autowired
//    HeartRepository heartRepository;
//    @Autowired
//    BoardService boardService;
//    @Autowired
//    BoardImgUrlsRepository boardImgUrlsRepository;
//
//    @Test
//    public void 맵로딩될때패치조인으로_한번에_데이터받기() {
//        List<LoadingBoardMapResponseDto> responseDtos = new ArrayList<>();
//        List<Board> boards = boardRepository.findAllFetchJoin();
//        for (Board board : boards) {
////            Set<BoardDetailCommentsDto> detailCommentsDtos = Comment.toDtoList(board.getComments());
//            Set<BoardImgCommonRequestDto> imgCommonRequestDtos = BoardImgUrls.toDtoList(board.getBoardImgUrls());
//            responseDtos.add(new LoadingBoardMapResponseDto(board, false, imgCommonRequestDtos));
//        }
//
////        Assertions.assertThat(34).isEqualTo(responseDtos.size());
//    }
//
//    @Test
//    @Transactional
//    public void 무한스크롤() {
//        List<Board> boards = boardService.fetchPages(999L, 10);
//        for (int i=0; i<boards.size(); i++) {
//            System.out.println(boards.get(i).getId());
//            Set<BoardImgUrls> boardImgUrls = boards.get(i).getBoardImgUrls();
//            System.out.println(boardImgUrls.iterator().next().getImgUrl());
//            Set<Comment> comments = boards.get(i).getComments();
//            if (comments.size() != 0)
//                System.out.println(comments.iterator().next().getContent());
//        }
//    }
//
//    @Test
//    @Transactional
//    public void 댓글최신순정렬() {
//        Board findBoard = boardRepository.findById(39L).orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다."));
//        Set<Comment> boardComments = findBoard.getComments();
//        List<Comment> collect = boardComments.stream().sorted(Comparator.comparing(Timestamped::getModified).reversed()).collect(Collectors.toList());// 정렬
//        for (Comment comment : collect) {
//            System.out.println(comment.getModified());
//        }
//        System.out.println();
//    }
//
//    @Test
//    public void 이미지삭제() {
////        boardImgUrlsRepository.deleteById(136L);
//    }
//}