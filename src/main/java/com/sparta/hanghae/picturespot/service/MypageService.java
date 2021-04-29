package com.sparta.hanghae.picturespot.service;

import com.sparta.hanghae.picturespot.dto.BoardDto;
import com.sparta.hanghae.picturespot.dto.CommentDto;
import com.sparta.hanghae.picturespot.model.Board;
import com.sparta.hanghae.picturespot.model.Comment;
import com.sparta.hanghae.picturespot.model.Heart;
import com.sparta.hanghae.picturespot.model.User;
import com.sparta.hanghae.picturespot.repository.BoardRepository;
import com.sparta.hanghae.picturespot.repository.CommentRepository;
import com.sparta.hanghae.picturespot.repository.HeartRepository;
import com.sparta.hanghae.picturespot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MypageService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final HeartRepository heartRepository;
    private final UserRepository userRepository;

    //내 명소(내가올린게시물) + 댓글 + 좋아요 + user정보(이름, 프로필사진, intro메시지)
    public List<BoardDto> getMyboard(User user){
        List<Board> boardList = boardRepository.findByUserOrderByModifiedDesc(user); //user로 게시물 찾는다
        List<BoardDto> boardDtoList = new ArrayList<>();
        for (Board board : boardList){
            Long boardId = board.getId();
            List<Comment> comments = commentRepository.findAllByBoardIdOrderByModifiedDesc(boardId);
            //게시물 아이디로 해당 게시물에 달린 댓글들 찾는다
            List<CommentDto> commentDtos = new ArrayList<>();
            for (Comment comment : comments){
                CommentDto commentDto = new CommentDto(comment);
                commentDtos.add(commentDto); //댓글을 dto리스트에 담는다
            }
            BoardDto boardDto = new BoardDto(board, commentDtos); //게시글 dto에 게시글과 댓글 dto리스트 담는다
            boardDtoList.add(boardDto); //게시글 dto리스트에 게시글 dto담는다
        }
        return boardDtoList;
    }

    //찜 명소(좋아요한 게시물) + 댓글 + 좋아요 + user정보
    public List<BoardDto> getMylikeboard(User user){
        List<BoardDto> boardDtoList = new ArrayList<>();
        List<Heart> heartList = heartRepository.findAllByUserAndLikedOrderByModifiedDesc(user,true);

        for (Heart hearts : heartList){
            Long boardId = hearts.getBoard().getId();
            Board board = boardRepository.findById(boardId).orElseThrow(
                    () -> new IllegalArgumentException("찜한 명소가 없습니다.")
            );
            List<Comment> commentList = commentRepository.findAllByBoardIdOrderByModifiedDesc(boardId);
            //게시물 아이디로 해당 게시물에 달린 댓글들 찾는다
            List<CommentDto> commentDtos = new ArrayList<>();
            for (Comment comment : commentList){
                CommentDto commentDto = new CommentDto(comment);
                commentDtos.add(commentDto); //댓글을 dto리스트에 담는다
            }
            BoardDto boardDto = new BoardDto(board, commentDtos); //게시글 dto에 게시글과 댓글 dto리스트 담는다
            boardDtoList.add(boardDto); //게시글 dto리스트에 게시글 dto담는다
        }
        return boardDtoList;
    }


    //프로필 수정
//    @Transactional
//    public ResponseEntity editProfile(User user, UserDto userDto){
//        User editUser = userRepository.findByUser(user);
//        //editUser.updateProfile(userDto);
//        return ResponseEntity.ok().build();
//    }
}
