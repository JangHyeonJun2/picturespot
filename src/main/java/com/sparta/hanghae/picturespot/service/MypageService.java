package com.sparta.hanghae.picturespot.service;


import com.sparta.hanghae.picturespot.dto.request.img.BoardImgCommonRequestDto;
import com.sparta.hanghae.picturespot.dto.request.mypage.NicknameRequestDto;
import com.sparta.hanghae.picturespot.dto.request.mypage.PasswordRequestDto;
import com.sparta.hanghae.picturespot.dto.request.mypage.ProfileRequestDto;
import com.sparta.hanghae.picturespot.dto.response.mypage.MypageCommentResponseDto;
import com.sparta.hanghae.picturespot.dto.response.mypage.MypageResponseDto;
import com.sparta.hanghae.picturespot.dto.response.mypage.NicknameResponseDto;
import com.sparta.hanghae.picturespot.dto.response.mypage.ProfileResponseDto;
import com.sparta.hanghae.picturespot.dto.response.question.Message;
import com.sparta.hanghae.picturespot.model.*;
import com.sparta.hanghae.picturespot.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class MypageService {

    private final BoardRepository boardRepository;
    private final HeartRepository heartRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardImgUrlsRepository boardImgUrlsRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    //user정보(이름, 프로필사진, intro메시지)
    public ProfileResponseDto getMyprofile(User user) {
        ProfileResponseDto profileResponseDto = new ProfileResponseDto(user);
        return profileResponseDto;
    }


    //내 명소(내가올린게시물) + 댓글 + 좋아요
    public List<MypageResponseDto> getMyboard(User user) {
        List<MypageResponseDto> mypageDtoList = new ArrayList<>();

        List<Board> boardList = boardRepository.findAllByUserIdOrderByModifiedDesc(user.getId()); //user로 게시물 찾는다


        for (Board board : boardList) { //게시글
            Long boardId = board.getId();
            Long userId = user.getId();

            List<BoardImgUrls> allBoardImgUrls = boardImgUrlsRepository.findAllByBoardId(board.getId()); //해당 board에 대한 이미지들 가져오기.
            List<Comment> comments = commentRepository.findAllByBoardIdOrderByModifiedDesc(boardId); //댓글
            List<MypageCommentResponseDto> commentResponseDtos = new ArrayList<>();
            List<BoardImgCommonRequestDto> requestDtos = new ArrayList<>();//board img list

            for (BoardImgUrls boardImgUrls : allBoardImgUrls) {
                requestDtos.add(new BoardImgCommonRequestDto(boardImgUrls));
            }

            for (Comment comment : comments) {
                MypageCommentResponseDto commentResponseDto = new MypageCommentResponseDto(comment);
                commentResponseDtos.add(commentResponseDto);
            }

            boolean likeCheck = heartRepository.existsByBoardIdAndUserId(boardId, userId); //좋아요 여부
            List<Heart> hearts = heartRepository.findAllByBoardId(boardId); //좋아요 수

            MypageResponseDto mypageDto = new MypageResponseDto(board, commentResponseDtos, likeCheck, hearts.size(), requestDtos);
            //user정보, 게시글, 댓글, 좋아요 여부, 좋아요 수
            mypageDtoList.add(mypageDto);
        }
        return mypageDtoList;


    }


    //찜 명소(좋아요한 게시물) + 댓글 + 좋아요, 내가 올린 게시물은 제외
    public List<MypageResponseDto> getMylikeboard(User user) {
        List<MypageResponseDto> mypageDtoList = new ArrayList<>();

        List<Heart> heartList = heartRepository.findAllByUser(user); //user가 누른 하트 찾기

        for (Heart hearts : heartList) {
            Long boardId = hearts.getBoard().getId(); //user가 누른 하트에서 게시물 아이디 찾기

            Board board = boardRepository.findByIdOrderByModifiedDesc(boardId).orElseThrow(//user가 좋아요 한 게시물
                    () -> new IllegalArgumentException("찜한 명소가 없습니다.")
            );
            if (!board.getUser().getId().equals(user.getId())) { //게시물 작성자와 현재 user가 다를 때만(남이 쓴 게시물만)
                List<Comment> comments = commentRepository.findAllByBoardIdOrderByModifiedDesc(board.getId()); //댓글
                List<BoardImgUrls> allBoardImgUrls = boardImgUrlsRepository.findAllByBoardId(boardId);        //해당 게시물에 대한 이미지들 가져오기.
                List<MypageCommentResponseDto> commentResponseDtos = new ArrayList<>();
                List<BoardImgCommonRequestDto> requestDtos = new ArrayList<>();

                for (BoardImgUrls boardImgUrls : allBoardImgUrls) {
                    requestDtos.add(new BoardImgCommonRequestDto(boardImgUrls));
                }

                for (Comment comment : comments) {
                    MypageCommentResponseDto commentResponseDto = new MypageCommentResponseDto(comment);
                    commentResponseDtos.add(commentResponseDto);
                }

                List<Heart> heartsList = heartRepository.findAllByBoardId(boardId);
                MypageResponseDto mypageDto = new MypageResponseDto(board, commentResponseDtos, true, heartsList.size(), requestDtos);
                //user정보, 게시글, 댓글, 좋아요 여부(true), 좋아요 수
                mypageDtoList.add(mypageDto);

            }
        }
        return mypageDtoList;
    }

    //프로필 편집(사진, 소개)
    @Transactional
    public ProfileResponseDto editProfile(String imgUrl, String introduceMsg, User user) {
        User editUser = userRepository.findById(user.getId()).orElseThrow(
                () -> new IllegalArgumentException("해당하는 user가 없습니다.")
        );
        ProfileRequestDto profileDto = new ProfileRequestDto(imgUrl, introduceMsg);
        editUser.updateProfile(profileDto);
        ProfileResponseDto profileResponseDto = new ProfileResponseDto(editUser);
        return profileResponseDto;
    }

    //닉네임 변경
    @Transactional
    public NicknameResponseDto editNick(NicknameRequestDto nickRequestDto, User user) {
        User editUser = userRepository.findById(user.getId()).orElseThrow(
                () -> new IllegalArgumentException("해당 user가 없습니다.")
        );
        editUser.updateNick(nickRequestDto);
        NicknameResponseDto nicknameResponseDto = new NicknameResponseDto(editUser);
        return nicknameResponseDto;
    }

    //비밀번호 변경
    @Transactional
    public ResponseEntity editPwd(PasswordRequestDto pwdRequestDto, User user) {
        User editUser = userRepository.findById(user.getId()).orElseThrow(
                () -> new IllegalArgumentException("해당 user가 없습니다.")
        );

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (!encoder.matches(pwdRequestDto.getPwd(), user.getPassword())) {
            Message message = new Message("비밀번호가 틀렸습니다.");
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (!pwdRequestDto.getNewPwd().equals(pwdRequestDto.getPwdChk())) {
            Message message = new Message("비밀번호가 일치하지 않습니다.");
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        String newPw = bCryptPasswordEncoder.encode(pwdRequestDto.getNewPwd());
        editUser.updatePw(newPw);
        Message message = new Message("비밀번호가 변경되었습니다.");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }


}
