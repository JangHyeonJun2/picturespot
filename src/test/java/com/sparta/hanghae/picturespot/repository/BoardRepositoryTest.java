package com.sparta.hanghae.picturespot.repository;

import com.sparta.hanghae.picturespot.model.Board;
import com.sparta.hanghae.picturespot.model.BoardImgUrls;
import com.sparta.hanghae.picturespot.model.Comment;
import com.sparta.hanghae.picturespot.service.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@SpringBootTest
//@TestPropertySource(properties = "spring.jpa.properties.hibernate.default_batch_fetch_size=1000") // 옵션 적용
class BoardRepositoryTest {
    @Autowired
    BoardService boardService;
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    public void 페치테스트() {
//        List<Board> all = boardRepository.findAllJoinFetch();
//        System.out.println(all.size());

        PageRequest pageRequest = PageRequest.of(0, 3);

        List<Board> allFetchJoin = boardRepository.findAllFetchJoin();
//        Assertions.assertThat(34).isEqualTo(allFetchJoin.size());
//        List<Board> ac = boardRepository.findByIdLessThan(999L);
//
//        Page<Board> byBoardIn = boardRepository.findByIdInOrderByIdDesc(ac, pageRequest);
//        for (int i=0 ;i<byBoardIn.getSize(); i++) {
//            System.out.println(byBoardIn.getContent().get(i).getId());
//        }
//
//        List<Board> byIdLessThanOrderByIdDesc = boardRepository.findByIdLessThanOrderByIdDesc(33L, pageRequest);
//        for (int i=0; i<3; i++) {
//            System.out.println(byIdLessThanOrderByIdDesc.get(i).getId());
//        }

//        List<User> collect = boardRepository.findAll().stream().map(Board::getUser).collect(Collectors.toList());
//        List<Board> list = boardRepository.findByIdLessThanAndUserInOrderByIdDesc(999L, collect, pageRequest);
//        for (int i=0; i<list.size(); i++) {
//            System.out.println(list.get(i).getId());
//        }


//        List<Comment> targetList = new ArrayList<>(ac.get(1).getComments());

//
//        Board allByFetchJoin = boardRepository.findByIdFetchJoin(20L);
//        for (int i=0; i<allByFetchJoin.size(); i++) {
//            System.out.println(allByFetchJoin.get(i).getId());
//        }
//        System.out.println(allByFetchJoin.getId());
//        System.out.println(allByFetchJoin.getTitle());
//        assertThat(1).isEqualTo(ac.get(1).getComments().size());

//        for (int j=0; j<ac.size(); j++) {
//            System.out.println(ac.get(j).getId());
//
//        }
//        System.out.println(ac.size());

//        List<Board> all = boardRepository.findAll(pageRequest).getContent();
//        for (Board b : all) {
//            System.out.println("b = " + b.getTitle() + "| comments =" + b.getComments().size());
//            for (Comment comment : b.getComments()) {
//                System.out.println("    =>comment = " + comment);
//            }
//        }
    }

    @Test
    @Transactional
    public void 상세보기패치조인() {
//        Board board = boardRepository.findById(11L).orElseThrow(() -> new IllegalArgumentException("해당 게시물은 없습니다."));
//        Assertions.assertThat(11).isEqualTo(board.getId());


//        List<Board> boards = boardRepository.findAllByOrderByModifiedDesc();
//        for (int i=0; i<boards.size(); i++) {
//            Set<BoardImgUrls> allBoardImgUrls = boards.get(i).getBoardImgUrls();
//            for (BoardImgUrls boardImgUrl : allBoardImgUrls) {
//                System.out.println(boardImgUrl.getImgUrl());
//            }
//        }

        Board board = boardRepository.findById(11L).orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다."));
        Set<BoardImgUrls> boardImgUrls = board.getBoardImgUrls();
        for (BoardImgUrls boardImgUrl : boardImgUrls) {
            System.out.println(boardImgUrl.getImgUrl());
        }

        Set<Comment> comments = board.getComments();
        for (Comment comment : comments) {
            System.out.println(comment.getContent());
        }
    }

    @Test
    public void 패치조인날짜순정렬() {
        List<Board> boards = boardRepository.findAllFetchJoinOrderByModifiedDesc();
    }

}