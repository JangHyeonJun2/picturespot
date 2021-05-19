package com.sparta.hanghae.picturespot.repository.repository;

import com.sparta.hanghae.picturespot.model.Board;
import com.sparta.hanghae.picturespot.repository.BoardRepository;
import com.sparta.hanghae.picturespot.service.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@SpringBootTest
class BoardRepositoryTest {
    @Autowired
    BoardService boardService;
    @Autowired
    BoardRepository boardRepository;

    @Test
    public void 페치테스트() {
        List<Board> all = boardRepository.findAll();
//        List<Board> all = boardRepository.findAllJoinFetch();
//        for (int i=0; i<5; i++) {
//            System.out.println(" 게시물 타이틀 : " + all.get(i).getTitle());
//            for (int j=0; j<all.get(i).getComments().size(); j++) {
//                System.out.println(" 게시물 댓글 " + all.get(i).getComments().get(j).getContent());
//            }
//            System.out.println("======================");
//        }

//        List<Board> boardList = boardRepository.findAllByFetchJoin();
//        for (Board board : boardList){
//            System.out.println("게시물 타이틀 : " + board.getTitle());
//            System.out.println("댓글 : "+ board.getComments());
//        }
//        PageRequest pageRequest = PageRequest.of(0, 3);
//        List<Board> ac = boardRepository.findAllEntityGraphWithUserByIdLessThanOrderByIdDesc(999L, pageRequest);
//        System.out.println(ac.size());

    }

}