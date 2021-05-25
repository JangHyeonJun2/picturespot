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

    //좋아요 클릭하기입니다.
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

    //좋아요 취소하기
    @Transactional
    public boolean deleteHeart(Long boardId, Long userId) {
        Board findBoard = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        User findUser = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

        Heart heart = checkAlreadyLike(findBoard, findUser);
        //만약 좋아요를 한 객체가 있으면 그 객체를 Heart테이블에서 삭제를 시키고 true 리턴. 그렇지 않으면 false 리턴.
        if (heart != null) {
            heartRepository.deleteById(heart.getId());
            return true;
        }else
            return false;

    }

    //사용자가 이미 좋아요 한 게시물인지 체크(boolean 값 받기)
    private boolean isNotAlreadyLike(Board findBoard, User findUser) {
        return heartRepository.existsByBoardIdAndUserId(findBoard.getId(), findUser.getId());
    }

    //사용자가 이미 좋아요 한 게시물인지 체크(Heart 객체 받기)
    private Heart checkAlreadyLike(Board findBoard, User findUser) {
        return heartRepository.findByBoardIdAndUserId(findBoard.getId(), findUser.getId());
    }
}
