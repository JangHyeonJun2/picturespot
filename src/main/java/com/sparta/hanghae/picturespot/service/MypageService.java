package com.sparta.hanghae.picturespot.service;

import com.sparta.hanghae.picturespot.dto.BoardDto;
import com.sparta.hanghae.picturespot.dto.UserDto;
import com.sparta.hanghae.picturespot.model.Board;
import com.sparta.hanghae.picturespot.model.Heart;
import com.sparta.hanghae.picturespot.model.User;
import com.sparta.hanghae.picturespot.repository.BoardRepository;
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
    private final HeartRepository heartRepository;
    private final UserRepository userRepository;

    //내 명소 + user정보
    public List<BoardDto> getMyboard(User user){
        List<Board> boardList = boardRepository.findByUser(user);
        List<BoardDto> boardDtoList = new ArrayList<>();
        for (Board board : boardList){
            BoardDto boardDto = new BoardDto(board);
            boardDtoList.add(boardDto);
        }
        return boardDtoList;
    }

    //찜 명소 + user정보
    public List<BoardDto> getMylikeboard(User user){
        List<Heart> heartList = new ArrayList<>();
        List<BoardDto> boardDtoList = new ArrayList<>();

        Heart heart = heartRepository.findByUserAndLiked(user,true);
        heartList.add(heart);

        for (Heart hearts : heartList){
            Long boardId = hearts.getBoard().getId();
            Board board = boardRepository.findById(boardId).orElseThrow(
                    () -> new IllegalArgumentException("찜한 명소가 없습니다.")
            );
            BoardDto boardDto = new BoardDto(board);
            boardDtoList.add(boardDto);
        }
        return boardDtoList;
    }

    //비밀번호 수정
    @Transactional
    public ResponseEntity editPassword(User user, UserDto userDto){
        User editUser = userRepository.findByUser(user);
        editUser.updatePwd(userDto);
        return ResponseEntity.ok().build();
    }

    //프로필 수정
    @Transactional
    public ResponseEntity editProfile(User user, UserDto userDto){
        User editUser = userRepository.findByUser(user);
        editUser.updateProfile(userDto);
        return ResponseEntity.ok().build();
    }
}
