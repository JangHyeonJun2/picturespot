package com.sparta.hanghae.picturespot.controller;

import com.sparta.hanghae.picturespot.dto.request.board.BoardSaveRequestDto;
import com.sparta.hanghae.picturespot.dto.request.board.BoardUpdateRequestDto;
import com.sparta.hanghae.picturespot.dto.response.board.*;
import com.sparta.hanghae.picturespot.model.Board;
import com.sparta.hanghae.picturespot.model.BoardImgUrls;
import com.sparta.hanghae.picturespot.model.User;
import com.sparta.hanghae.picturespot.model.UserPrincipal;
import com.sparta.hanghae.picturespot.repository.BoardRepository;
import com.sparta.hanghae.picturespot.repository.UserRepository;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class BoardController {
    private final BoardService boardService;
    private final CustomExceptionController customExceptionController;
    private final S3Service s3Service;
    private final UserRepository userRepository;


    //게시글(커뮤니티)페이지
    @GetMapping("/board")
    public ResponseEntity getBoards(@AuthenticationPrincipal UserPrincipal user) {
        List<BoardsGetResponseDto> boards = boardService.getBoards(user);
        return customExceptionController.ok("게시글 정보 입니다!!.", boards);
    }

    //게시물 작성
    @PostMapping("/board")
    public ResponseEntity save(@RequestParam(value = "file", required = false) MultipartFile[] files, @RequestParam("title") String title,
                               @RequestParam("content") String content, @RequestParam("category") String category, @RequestParam("latitude") double latitude,
                               @RequestParam("longitude") double longitude, @RequestParam("spotName") String spotName, @AuthenticationPrincipal UserPrincipal user) throws IOException {

        String[] imgUrls = s3Service.upload(Arrays.asList(files), "board");

        User findUser = findUserMethod(user);
        BoardSaveRequestDto boardSaveRequestDto = new BoardSaveRequestDto(title,content,category,latitude,longitude, spotName, findUser);
        BoardSaveResponseDto boardSaveResponseDto = boardService.save(boardSaveRequestDto, imgUrls);
        return customExceptionController.ok("게시물을 저장하였습니다.", boardSaveResponseDto);
    }

    //    게시글 수정
    @PutMapping("/board/{boardId}")
    public ResponseEntity update(@PathVariable Long boardId, @RequestParam(value = "file", required = false) MultipartFile[] files,
                                 @RequestParam("title") String title, @RequestParam("content") String content,
                                 @RequestParam(value = "deleteImages", required = false) Long[] deleteImages,
                                 @AuthenticationPrincipal UserPrincipal user) throws IOException {

        User findUser = findUserMethod(user);
        //s3에 이미지를 삭제하는 메서드
        if (deleteImages != null)
            s3Service.findImgUrls(deleteImages);
        //s3에 이미지 업로드하고 업로드된 이미지 배열
        String[] imgUrls =null;
        if (files != null) {
            imgUrls = s3Service.upload(Arrays.asList(files), "board"); //새로 추가된 이미지 s3에 저장.
        }

        BoardUpdateRequestDto boardUpdateRequestDto = new BoardUpdateRequestDto(boardId,title,content);
        BoardDetailResponseDto updateBoard = boardService.update(boardUpdateRequestDto, findUser, deleteImages, imgUrls);

        if (updateBoard == null)
            return customExceptionController.error("사용자가 옳바르지 않습니다.");
        else
            return customExceptionController.ok("게시글이 수정되었습니다.", updateBoard);
    }

    //게시글 삭제
    @DeleteMapping("/board/{boardId}")
    public ResponseEntity delete(@PathVariable Long boardId, @AuthenticationPrincipal UserPrincipal user) {
        User userMethod = findUserMethod(user);
        Long deleteBoardId = boardService.delete(boardId, userMethod.getId());
        return customExceptionController.ok("게시물이 삭제되었습니다.", deleteBoardId);
    }

    //게시글 검색(제목 + 내용)
    @GetMapping("/board/search")
    public ResponseEntity search(@RequestParam("searchText") String searchText, @AuthenticationPrincipal UserPrincipal user){
        //검색어가 비어있을 때
        if (searchText.isEmpty()){
            return customExceptionController.error("검색어가 비어있습니다.");
        }
        User findUser = findUserMethod(user);
        List<BoardGetSearchResponseDto> searchBoardList = boardService.search(searchText, findUser);
        return customExceptionController.ok("검색 결과 입니다." , searchBoardList);
    }

    //게시글 상세 페이지
    @GetMapping("/board/{boardId}/detail")
    public ResponseEntity detail(@PathVariable Long boardId, @AuthenticationPrincipal UserPrincipal user) {

        BoardDetailResponseDto detail = boardService.detail(boardId, user);
        return customExceptionController.ok("해당 게시글 상세페이지 정보입니다..", detail);
    }

    //지도페이지 로딩 될 때
    @GetMapping("/map")
    public ResponseEntity loadingMapBoard(@AuthenticationPrincipal UserPrincipal user) {
//        User findUser = findUserMethod(user);
        List<LoadingBoardMapResponseDto> loadingBoardMapResponseDtos = boardService.loadingMapBoard(user);
        return customExceptionController.ok("모든 게시물 데이터 정보입니다." ,loadingBoardMapResponseDtos);
    }

    //무한스크롤
    @GetMapping("/board/community/scroll")
    public ResponseEntity boardCommunityScroll(@AuthenticationPrincipal UserPrincipal user, @RequestParam Long lastBoardId, @RequestParam int size) {
        List<BoardListGetResponseDto> responseDtoList = boardService.fetchBoardPage(lastBoardId, size, user);
        return customExceptionController.ok("무한스크롤 데이터!!",responseDtoList);
    }

    public  User findUserMethod(UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));
    }

}

