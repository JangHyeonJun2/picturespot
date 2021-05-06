package com.sparta.hanghae.picturespot.controller;

import com.sparta.hanghae.picturespot.dto.request.board.BoardSaveRequestDto;
import com.sparta.hanghae.picturespot.dto.response.board.*;
import com.sparta.hanghae.picturespot.model.User;
import com.sparta.hanghae.picturespot.responseentity.CustomExceptionController;
import com.sparta.hanghae.picturespot.service.BoardService;
import com.sparta.hanghae.picturespot.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class BoardController {
    private final BoardService boardService;
    private final CustomExceptionController customExceptionController;
    private final S3Service s3Service;

    //게시글(커뮤니티)페이지
    @GetMapping("/board")
    public ResponseEntity getBoards(@AuthenticationPrincipal User user) {
        List<BoardsGetResponseDto> boards = boardService.getBoards(user);
        return customExceptionController.ok("게시글 정보 입니다.", boards);
    }

    //게시물 작성
    //TODO 클라인트분들에게 장소도 넘겨주는지? 물어보기
    @PostMapping("/board")
    public ResponseEntity save(@RequestParam(value = "file", required = false) List<MultipartFile> files, @RequestParam("title") String title,
                               @RequestParam("content") String content, @RequestParam("category") String category, @RequestParam("latitude") BigDecimal latitude,
                               @RequestParam("longitude") BigDecimal longitude, @RequestParam("spotName") String spotName, @AuthenticationPrincipal User user) throws IOException {


        String[] imgUrls = s3Service.upload(files, "board");

        BoardSaveRequestDto boardSaveRequestDto = new BoardSaveRequestDto(title,content,category,latitude,longitude, spotName, user);
        BoardSaveResponseDto responseDto = boardService.save(boardSaveRequestDto, imgUrls);
        return customExceptionController.ok("게시물을 저장하였습니다.", responseDto);
    }

    //게시글 수정
//    @PutMapping("/board/{boardId}")
//    public ResponseEntity update(@PathVariable String boardId) {
//
//    }

    //게시글 삭제
    @DeleteMapping("/board/{boardId}")
    public ResponseEntity delete(@PathVariable Long boardId, @AuthenticationPrincipal User user) {
        Long deleteBoardId = boardService.delete(boardId, user.getId());
        return customExceptionController.ok("게시물이 삭제되었습니다.", deleteBoardId);
    }

    //게시글 검색(제목 + 내용)
    @GetMapping("/board/search")
    public ResponseEntity search(@RequestParam("searchText") String searchText, @AuthenticationPrincipal User user){
        //검색어가 비어있을 때
        if (searchText.isEmpty()){
            return customExceptionController.error("검색어가 비어있습니다.");
        }
        List<BoardGetSearchResponseDto> searchBoardList = boardService.search(searchText, user);
        return customExceptionController.ok("검색 결과 입니다." , searchBoardList);
    }

    //게시글 상세 페이지
    @GetMapping("/board/{boardId}/detail")
    public ResponseEntity detail(@PathVariable Long boardId, @AuthenticationPrincipal User user) {
        BoardDetailResponseDto detail = boardService.detail(boardId, user);
        return customExceptionController.ok("해당 게시글 상세페이지 정보입니다..", detail);
    }

    //지도페이지 로딩 될 때
    @GetMapping("/map")
    public ResponseEntity loadingMapBoard(@AuthenticationPrincipal User user) {
        List<LoadingBoardMapResponseDto> loadingBoardMapResponseDtos = boardService.loadingMapBoard(user);
        return customExceptionController.ok("모든 게시물 데이터 정보입니다." ,loadingBoardMapResponseDtos);
    }
}

