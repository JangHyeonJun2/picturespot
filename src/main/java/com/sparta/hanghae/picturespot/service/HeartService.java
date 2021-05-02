package com.sparta.hanghae.picturespot.service;

import com.sparta.hanghae.picturespot.model.Board;
import com.sparta.hanghae.picturespot.model.Heart;
import com.sparta.hanghae.picturespot.model.User;
import com.sparta.hanghae.picturespot.repository.BoardRepository;
import com.sparta.hanghae.picturespot.repository.HeartRepository;
import com.sparta.hanghae.picturespot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class HeartService {
    private final HeartRepository heartRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    //좋아요 클릭하기
    @Transactional
    public boolean addHeart(Long boardId, Long userId) {
        Board findBoard = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        User findUser = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

        //좋아요 중복 방지
        if (!isNotAlreadyLike(findBoard, findUser)) {
            heartRepository.save(new Heart(findUser, findBoard));
            return true;
        }
        //이미 좋아요를 했던 게시물이 있으면 false를 return
        return false;
    }

    //사용자가 이미 좋아요 한 게시물인지 체크
    private boolean isNotAlreadyLike(Board findBoard, User findUser) {
        return heartRepository.existsByBoardIdAndUserId(findBoard.getId(), findUser.getId());
    }
}
