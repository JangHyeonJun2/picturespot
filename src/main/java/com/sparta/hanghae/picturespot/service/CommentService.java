package com.sparta.hanghae.picturespot.service;

import com.sparta.hanghae.picturespot.dto.request.Comment.CommentSaveRequestDto;
import com.sparta.hanghae.picturespot.dto.request.Comment.CommentUpdateRequestDto;
import com.sparta.hanghae.picturespot.dto.response.comment.CommentSaveResponseDto;
import com.sparta.hanghae.picturespot.dto.response.comment.CommentUpdateResponseDto;
import com.sparta.hanghae.picturespot.model.Board;
import com.sparta.hanghae.picturespot.model.Comment;
import com.sparta.hanghae.picturespot.model.User;
import com.sparta.hanghae.picturespot.repository.BoardRepository;
import com.sparta.hanghae.picturespot.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public CommentSaveResponseDto addComment(Long boardId, User user, CommentSaveRequestDto commentSaveRequestDto) {
        Board findBoard = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다.."));
        commentSaveRequestDto.addBoardInComment(findBoard);
        commentSaveRequestDto.addUserInComment(user);
        Comment saveComment = commentRepository.save(commentSaveRequestDto.toEntity());
        return new CommentSaveResponseDto(saveComment);
    }

    @Transactional
    public CommentUpdateResponseDto updateComment(Long commentId, CommentUpdateRequestDto requestDto, User user) {
        Comment findComment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다."));

        if (user.getId().equals(findComment.getUser().getId())) {
            Comment updateComment = findComment.update(requestDto);
            return new CommentUpdateResponseDto(updateComment);
        } else
            return null;
    }
}
