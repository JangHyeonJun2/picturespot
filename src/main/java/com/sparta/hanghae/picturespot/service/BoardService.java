package com.sparta.hanghae.picturespot.service;

import com.sparta.hanghae.picturespot.dto.request.board.BoardUpdateRequestDto;
import com.sparta.hanghae.picturespot.dto.request.img.BoardImgCommonRequestDto;
import com.sparta.hanghae.picturespot.dto.request.board.BoardSaveRequestDto;
import com.sparta.hanghae.picturespot.dto.request.img.BoardImgSaveRequestDto;
import com.sparta.hanghae.picturespot.dto.response.board.*;
import com.sparta.hanghae.picturespot.model.*;
import com.sparta.hanghae.picturespot.repository.*;
import com.sparta.hanghae.picturespot.responseentity.CustomExceptionController;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final HeartRepository heartRepository;
    private final CommentRepository commentRepository;
    private final BoardImgUrlsRepository boardImgUrlsRepository;
    private final CustomExceptionController customExceptionController;

    //게시물 저장
    @Transactional
    public Long save(BoardSaveRequestDto requestDto, String[] imgUrls) {
        Board boardEntity = boardRepository.save(requestDto.toEntity());
        List<BoardImgSaveRequestDto> boardImgReponseDtoList = new ArrayList<>();

        for (String imgUrl : imgUrls) {
            BoardImgSaveRequestDto boardImgSaveRequestDto = new BoardImgSaveRequestDto(boardEntity, imgUrl);
            boardImgUrlsRepository.save(boardImgSaveRequestDto.toEntity());
            boardImgReponseDtoList.add(boardImgSaveRequestDto);
        }
        return boardEntity.getId();
    }

    //게시물 삭제
    @Transactional
    public Long delete(Long boardId, Long userId) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("게시물이 존재 하지 않습니다."));
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("계정이 존재하지 않습니다."));
        //board 와 boardImgUrl이 연관관계(즉 외래키 때문에) 해당 게시판의 이미지를 먼저 조회하고 삭제를 하고 게시판을 삭제해야한다.
//        List<BoardImgUrls> allByBoardId = boardImgUrlsRepository.findAllByBoardId(boardId);
//        for (int i=0; i<allByBoardId.size(); i++) {
//            boardImgUrlsRepository.delete(allByBoardId.get(i));
//        }
        //
        if (board.getUser().getId().equals(userId)) {
            boardRepository.deleteById(boardId);
            return board.getId();//삭제된 게시물 id 리턴.
        }
        else{
            return null;
        }
    }

    //커뮤니티 게시글 조회
    public List<BoardsGetResponseDto> getBoards(UserPrincipal loginUser) {
        List<BoardsGetResponseDto> boardGetResponseDtoList = new ArrayList<>();
        List<Board> boardAll = boardRepository.findAllByOrderByModifiedDesc();
        boolean likeCheck = true;
        for (int i=0; i<boardAll.size(); i++) {
            Set<BoardImgUrls> allBoardImgUrls = boardAll.get(i).getBoardImgUrls();
            Set<Comment> allComments = boardAll.get(i).getComments();

            List<BoardImgCommonRequestDto> boardImgCommonRequestDtos = new ArrayList<>();
            List<BoardDetailCommentsDto> boardDetailCommentsDtos = new ArrayList<>();


            for (int j=0; j<allBoardImgUrls.size(); j++) {
                boardImgCommonRequestDtos.add(new BoardImgCommonRequestDto(allBoardImgUrls.iterator().next()));
            }
            for (int k=0; k<allComments.size(); k++) {
                boardDetailCommentsDtos.add(new BoardDetailCommentsDto(allComments.iterator().next()));
            }
            //로그인 사용자가 게시물을 좋아요 했는지 안했는지 체크!
            if (loginUser == null) {//로그인이 되어있지 않은 사용자일 때
                likeCheck = false;
            } else //로그인이 되어있는 사용자 일 때
                likeCheck = heartRepository.existsByBoardIdAndUserId(boardAll.get(i).getId(), loginUser.getId());
            //게시물에 대한 좋아요 개수
            List<Heart> allByBoardIdHearts = boardAll.get(i).getHearts();

            BoardsGetResponseDto brdto = new BoardsGetResponseDto(boardAll.get(i).getUser(), boardAll.get(i), likeCheck, allByBoardIdHearts.size(), boardDetailCommentsDtos,boardImgCommonRequestDtos);

            boardGetResponseDtoList.add(brdto);
        }

        return boardGetResponseDtoList;
    }

    //검색 게시물 조회
    public List<BoardGetSearchResponseDto> search(String searchText, User loginUser) {
        List<BoardGetSearchResponseDto> searchResponseDtos = new ArrayList<>();

//        searchText = "%" + searchText + "%";
        List<Board> findSearchBoardList = boardRepository.findByTitleContainingOrContentContainingOrderByModifiedDesc(searchText, searchText); //OrderByModifiedDesc
//        List<Board> findSearchBoardList = boardRepository.findByTitleIsLikeOrContentIsLikeOrderByModifiedDesc(searchText, searchText); //OrderByModifiedDesc
        boolean likeCheck = true;
        for (int i=0; i<findSearchBoardList.size(); i++) {
            Set<BoardImgUrls> allBoardImgUrl = findSearchBoardList.get(i).getBoardImgUrls();
            Set<Comment> allBoardComments = findSearchBoardList.get(i).getComments();

            List<BoardImgCommonRequestDto> boardImgCommonRequestDtos = new ArrayList<>(); // 해당하는 boardImgUrls 담는 리스트
            List<BoardDetailCommentsDto> detailCommentsDtoList = new ArrayList<>(); //댓글 리스트

            for (int j=0; j<allBoardImgUrl.size(); j++) {//ImgUrl들 넣어주기.
                boardImgCommonRequestDtos.add(new BoardImgCommonRequestDto(allBoardImgUrl.iterator().next()));
            }

            for (int k=0; k<allBoardComments.size(); k++) {
                detailCommentsDtoList.add(new BoardDetailCommentsDto(allBoardComments.iterator().next()));
            }
            if (loginUser == null) {
                likeCheck = false;
            } else
                likeCheck = heartRepository.existsByBoardIdAndUserId(findSearchBoardList.get(i).getId(), loginUser.getId());
            //게시물에 대한 좋아요 개수
            List<Heart> allByBoardIdHeart = findSearchBoardList.get(i).getHearts();

            BoardGetSearchResponseDto responseDto = new BoardGetSearchResponseDto(findSearchBoardList.get(i), likeCheck, allByBoardIdHeart.size(), detailCommentsDtoList,boardImgCommonRequestDtos);
            searchResponseDtos.add(responseDto);
        }
        return searchResponseDtos;
    }
    //게시물 상세보기
    public BoardDetailResponseDto detail(Long boardId, UserPrincipal loginUser) {
        Board findBoard = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다."));

        //가져온 imgUrl 들을 담을 dto리스트 만들기.
        List<BoardImgCommonRequestDto> requestDtos = new ArrayList<>();
        //가져온 comment 들을 담을 dto리스트 만들기.
        List<BoardDetailCommentsDto> detailCommentsDtoList = new ArrayList<>();

        boolean likeCheck = true;


        //해당 board에 대한 imgUrl 들을 가져오기.
        Set<BoardImgUrls> boardImgUrls = findBoard.getBoardImgUrls();
        //해당 board에 대한 comment 들을 가져오기.
        Set<Comment> boardComments = findBoard.getComments();//TODO 최신순으로 다시 정렬



        //반목문을 돌려서 리스트에 imgUrl들을 담기.
        for (BoardImgUrls boardImgUrl : boardImgUrls) {
            requestDtos.add(new BoardImgCommonRequestDto(boardImgUrl));
        }

        for (Comment comment : boardComments) {
            detailCommentsDtoList.add(new BoardDetailCommentsDto(comment));
        }
        if (loginUser == null) {
            likeCheck = false;
        } else {
            likeCheck = heartRepository.existsByBoardIdAndUserId(findBoard.getId(), loginUser.getId());
        }

        //게시물에 대한 좋아요 개수
        List<Heart> allBoardHeartCount = findBoard.getHearts();

        return new BoardDetailResponseDto(findBoard,likeCheck,allBoardHeartCount.size(),detailCommentsDtoList,requestDtos);
    }
    //게시물 메인페이지(지도) 로딩될 때 데이터 보내주기
    public List<LoadingBoardMapResponseDto>loadingMapBoard(UserPrincipal loginUser) {
//        List<Board> boards = boardRepository.findAll();
//        List<LoadingBoardMapResponseDto> loadingBoardMapResponseDtos = new ArrayList<>();
//        boolean likeCheck = true;
//
//        for (int i=0; i<boards.size(); i++) {
//            Set<Comment> comments = boards.get(i).getComments();
//            Set<BoardImgUrls> BoardImgUrls = boards.get(i).getBoardImgUrls();
//
//            List<BoardDetailCommentsDto> detailCommentsDtoList = new ArrayList<>();
//            List<BoardImgCommonRequestDto> boardImgCommonRequestDtoList = new ArrayList<>();
//
//
//            for (BoardImgUrls boardImgUrls : BoardImgUrls) {
//                boardImgCommonRequestDtoList.add(new BoardImgCommonRequestDto(boardImgUrls));
//            }
//
//            for (Comment comment : comments) {
//                detailCommentsDtoList.add(new BoardDetailCommentsDto(comment));
//            }
//
//            if (loginUser == null) {
//                likeCheck = false;
//            } else {
//                likeCheck = heartRepository.existsByBoardIdAndUserId(boards.get(i).getId(), loginUser.getId());
//            }
//            List<Heart> allByBoardId = boards.get(i).getHearts();
//            LoadingBoardMapResponseDto boardMapResponseDto = new LoadingBoardMapResponseDto(boards.get(i), likeCheck, allByBoardId.size(), detailCommentsDtoList,boardImgCommonRequestDtoList);
//            loadingBoardMapResponseDtos.add(boardMapResponseDto);
//        }
//        return loadingBoardMapResponseDtos;

        List<LoadingBoardMapResponseDto> responseDtos = new ArrayList<>();
        List<Board> boards = boardRepository.findAllFetchJoin();
        boolean liked = true;
        for (Board board : boards) {
            Set<BoardDetailCommentsDto> detailCommentsDtos = Comment.toDtoList(board.getComments());
            Set<BoardImgCommonRequestDto> imgCommonRequestDtos = BoardImgUrls.toDtoList(board.getBoardImgUrls());
            if (loginUser == null)
                liked = false;
            else
                liked = heartRepository.existsByBoardIdAndUserId(board.getId(), loginUser.getId());
            responseDtos.add(new LoadingBoardMapResponseDto(board, liked, board.getHearts().size(), detailCommentsDtos, imgCommonRequestDtos));
        }
        return responseDtos;
    }

    //게시글 수정(이미지 삭제, 추가, 타이틀, 내용 수정)
    @Transactional
    public BoardDetailResponseDto update(BoardUpdateRequestDto boardUpdateRequestDto, User loginUser, Long[] deleteImgUrlId,  String[] imgUrls)  {
        Board board = boardRepository.findById(boardUpdateRequestDto.getBoardId()).orElseThrow(() -> new IllegalArgumentException("해당 게시물은 없습니다."));
        if (board.getUser().getId().equals(loginUser.getId())) {
            board.update(boardUpdateRequestDto);
        } else {
            return null;
        }
        //boardImgUrls 의 테이블에 deleteImgUrl 들을 삭제.
        if (deleteImgUrlId != null) {
            for (Long id : deleteImgUrlId) {
                boardImgUrlsRepository.deleteById(id);
            }
        }

        //boardImgUrls 의 테이블에 새로운 imgUrls 를 저장.
        if (imgUrls != null) {
            for (String imgUrl : imgUrls) {
                BoardImgSaveRequestDto boardImgSaveRequestDto = new BoardImgSaveRequestDto(board, imgUrl);
                boardImgUrlsRepository.save(boardImgSaveRequestDto.toEntity());
            }
        }

        List<BoardImgCommonRequestDto> boardImgCommonRequestDtoList = new ArrayList<>();
        List<BoardDetailCommentsDto> boardDetailCommentsDtoList = new ArrayList<>();
        boolean likeCheck = true;

        //해당 board에 대한 imgUrl들을 가져오기.
        Set<BoardImgUrls> allBoardImgs = board.getBoardImgUrls();

        //반목문을 돌려서 리스트에 imgUrl들을 담기.
        for (BoardImgUrls boardImgUrl : allBoardImgs) {
            boardImgCommonRequestDtoList.add(new BoardImgCommonRequestDto(boardImgUrl));
        }
        //해당 board에 대한 comment 들을 가져오기.
        Set<Comment> allComments = board.getComments();

        for (Comment comment : allComments) {
            boardDetailCommentsDtoList.add(new BoardDetailCommentsDto(comment));
        }
        if (loginUser == null) {
            likeCheck = false;
        } else {
            likeCheck = heartRepository.existsByBoardIdAndUserId(board.getId(), loginUser.getId());
        }

        //게시물에 대한 좋아요 개수
        List<Heart> allBoardHeartCount = board.getHearts();
        return new BoardDetailResponseDto(board,likeCheck,allBoardHeartCount.size(),boardDetailCommentsDtoList,boardImgCommonRequestDtoList);
    }

    public List<BoardListGetResponseDto> fetchBoardPage(Long lastBoardId, int size, UserPrincipal loginUser) {
        List<Board> boards = fetchPages(lastBoardId, size);
        List<BoardListGetResponseDto> infinityScrollDto = new ArrayList<>();
        BoardListGetResponseDto boardListGetResponseDto;
        boolean likeCheck = true;

        for (int i=0; i<boards.size(); i++) {
            //로그인에 따라 좋아요 판별!
            if (loginUser == null) {
                likeCheck = false;
            } else {
                likeCheck = heartRepository.existsByBoardIdAndUserId(boards.get(i).getId(), loginUser.getId());
            }
            boardListGetResponseDto = new BoardListGetResponseDto(boards.get(i), likeCheck, boards.get(i).getHearts().size());
            infinityScrollDto.add(boardListGetResponseDto);
        }
        return infinityScrollDto;
    }

    public List<Board> fetchPages(Long lastBoardId, int size) {
        PageRequest pageRequest = PageRequest.of(0, size);
        return boardRepository.findByIdLessThanOrderByIdDesc(lastBoardId,pageRequest);
    }
}
