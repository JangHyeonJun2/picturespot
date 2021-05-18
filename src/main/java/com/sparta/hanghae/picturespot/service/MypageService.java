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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


@RequiredArgsConstructor
@Service
public class MypageService {

    private final BoardRepository boardRepository;
    private final HeartRepository heartRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardImgUrlsRepository boardImgUrlsRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final S3Service s3Service;
    private final UserService userService;


    //user정보(이름, 프로필사진, intro메시지)
    public ProfileResponseDto getMyprofile(Long userId) {
        User findUser = userRepository.findById(userId).orElseThrow(
                ()->new IllegalArgumentException("해당 사용자가 없습니다."));
        ProfileResponseDto profileResponseDto = new ProfileResponseDto(findUser);
        return profileResponseDto;
    }


    //내 명소(내가올린게시물)  + 좋아요
    public List<MypageResponseDto> getMyboard(Long lastId, int size, Long userId, UserPrincipal user) {
        User findUser = userRepository.findById(userId).orElseThrow(
                ()->new IllegalArgumentException("해당 사용자가 없습니다.")); //findUser : 그 사람의 페이지, user : 현재 로그인한 사용자

        Pageable pageable = PageRequest.of(0, size);
        Page<Board> boardList = boardRepository.findByIdLessThanAndUserIdOrderByModifiedDesc(lastId, findUser.getId(), pageable);
        //List<Board> boardList = boardRepository.findAllByUserIdOrderByModifiedDesc(findUser.getId()); //user로 게시물 찾는다
        List<MypageResponseDto> mypageDtoList = new ArrayList<>();

        for (Board board : boardList) { //게시글
            Long boardId = board.getId();
            Set<BoardImgUrls> allBoardImgUrls = board.getBoardImgUrls();

            List<BoardImgCommonRequestDto> requestDtos = new ArrayList<>();//board img list
            for (BoardImgUrls boardImgUrls : allBoardImgUrls) {
                requestDtos.add(new BoardImgCommonRequestDto(boardImgUrls));
            }

            if (user==null){
                boolean likeCheck = false;
                List<Heart> hearts = board.getHearts();
                //List<Heart> hearts = heartRepository.findAllByBoardId(boardId); //좋아요 수
                MypageResponseDto mypageDto = new MypageResponseDto(board, likeCheck, hearts.size(), requestDtos);
                //user정보, 게시글, 댓글, 좋아요 여부, 좋아요 수
                mypageDtoList.add(mypageDto);
            }else{
                boolean likeCheck = heartRepository.existsByBoardIdAndUserId(boardId, user.getId()); //좋아요 여부
                List<Heart> hearts = board.getHearts();
                MypageResponseDto mypageDto = new MypageResponseDto(board, likeCheck, hearts.size(), requestDtos);
                //user정보, 게시글, 댓글, 좋아요 여부, 좋아요 수
                mypageDtoList.add(mypageDto);
            }
        }
        return mypageDtoList;
    }
//    public List<MypageResponseDto> getMyboard(Long userId, UserPrincipal user) {
//        User findUser = userRepository.findById(userId).orElseThrow(
//                ()->new IllegalArgumentException("해당 사용자가 없습니다."));
//        //findUser : 그 사람의 페이지, user : 현재 로그인한 사용자
//        List<MypageResponseDto> mypageDtoList = new ArrayList<>();
//        List<Board> boardList = boardRepository.findAllByUserIdOrderByModifiedDesc(findUser.getId()); //user로 게시물 찾는다
//        for (Board board : boardList) { //게시글
//            Long boardId = board.getId();
//
//            List<BoardImgUrls> allBoardImgUrls = boardImgUrlsRepository.findAllByBoardId(board.getId()); //해당 board에 대한 이미지들 가져오기.
//            List<BoardImgCommonRequestDto> requestDtos = new ArrayList<>();//board img list
//            for (BoardImgUrls boardImgUrls : allBoardImgUrls) {
//                requestDtos.add(new BoardImgCommonRequestDto(boardImgUrls));
//            }
//
//            if (user==null){
//                boolean likeCheck = false;
//                List<Heart> hearts = heartRepository.findAllByBoardId(boardId); //좋아요 수
//                MypageResponseDto mypageDto = new MypageResponseDto(board, likeCheck, hearts.size(), requestDtos);
//                //user정보, 게시글, 댓글, 좋아요 여부, 좋아요 수
//                mypageDtoList.add(mypageDto);
//            }else{
//                boolean likeCheck = heartRepository.existsByBoardIdAndUserId(boardId, user.getId()); //좋아요 여부
//                List<Heart> hearts = heartRepository.findAllByBoardId(boardId); //좋아요 수
//                MypageResponseDto mypageDto = new MypageResponseDto(board, likeCheck, hearts.size(), requestDtos);
//                //user정보, 게시글, 댓글, 좋아요 여부, 좋아요 수
//                mypageDtoList.add(mypageDto);
//            }
//        }
//        return mypageDtoList;
//    }



    //찜 명소(좋아요한 게시물) + 댓글 + 좋아요, 내가 올린 게시물은 제외
    public List<MypageResponseDto> getMylikeboard(Long lastId, int size, Long userId, UserPrincipal user) {
        User findUser = userRepository.findById(userId).orElseThrow(
                ()->new IllegalArgumentException("해당 사용자가 없습니다."));
        //findUser : 그 사람의 페이지, user : 현재 로그인한 사용자
        List<MypageResponseDto> mypageDtoList = new ArrayList<>();

        Pageable pageable = PageRequest.of(0, size);
        Page<Heart> heartList = heartRepository.findByIdLessThanAndUserIdOrderByIdDesc(lastId, userId, pageable);

        //List<Heart> heartList = heartRepository.findAllByUser(findUser); //user가 누른 하트 찾기
        for (Heart hearts : heartList) {
            Board board = hearts.getBoard();
            if (!board.getUser().getId().equals(findUser.getId())) { //게시물 작성자와 페이지 user가 다를 때만(남이 쓴 게시물만)
                Set<BoardImgUrls> allBoardImgUrls = board.getBoardImgUrls();

                List<BoardImgCommonRequestDto> requestDtos = new ArrayList<>();
                for (BoardImgUrls boardImgUrls : allBoardImgUrls) {
                    requestDtos.add(new BoardImgCommonRequestDto(boardImgUrls));
                }

                if (user==null){
                    boolean likeCheck = false;
                    List<Heart> heartsList = board.getHearts(); //좋아요 수
                    MypageResponseDto mypageDto = new MypageResponseDto(board, likeCheck, heartsList.size(), requestDtos);
                    //user정보, 게시글, 댓글, 좋아요 여부, 좋아요 수
                    mypageDtoList.add(mypageDto);
                }else{
                    boolean likeCheck = heartRepository.existsByBoardIdAndUserId(board.getId(), user.getId()); //좋아요 여부
                    List<Heart> heartsList = board.getHearts();; //좋아요 수
                    MypageResponseDto mypageDto = new MypageResponseDto(board, likeCheck, heartsList.size(), requestDtos);
                    //user정보, 게시글, 댓글, 좋아요 여부, 좋아요 수
                    mypageDtoList.add(mypageDto);
                }
            }
        }
        return mypageDtoList;
    }
//    public List<MypageResponseDto> getMylikeboard(Long userId, UserPrincipal user) {
//        User findUser = userRepository.findById(userId).orElseThrow(
//                ()->new IllegalArgumentException("해당 사용자가 없습니다."));
//        //findUser : 그 사람의 페이지, user : 현재 로그인한 사용자
//        List<MypageResponseDto> mypageDtoList = new ArrayList<>();
//        List<Heart> heartList = heartRepository.findAllByUser(findUser); //user가 누른 하트 찾기
//        for (Heart hearts : heartList) {
//            Long boardId = hearts.getBoard().getId(); //user가 누른 하트에서 게시물 아이디 찾기
//            Board board = boardRepository.findByIdOrderByModifiedDesc(boardId).orElseThrow(//user가 좋아요 한 게시물
//                    () -> new IllegalArgumentException("찜한 명소가 없습니다.")
//            );
//            if (!board.getUser().getId().equals(findUser.getId())) { //게시물 작성자와 페이지 user가 다를 때만(남이 쓴 게시물만)
//                List<BoardImgUrls> allBoardImgUrls = boardImgUrlsRepository.findAllByBoardId(boardId);        //해당 게시물에 대한 이미지들 가져오기.
//                List<BoardImgCommonRequestDto> requestDtos = new ArrayList<>();
//                for (BoardImgUrls boardImgUrls : allBoardImgUrls) {
//                    requestDtos.add(new BoardImgCommonRequestDto(boardImgUrls));
//                }
//
//                if (user==null){
//                    boolean likeCheck = false;
//                    List<Heart> heartsList = heartRepository.findAllByBoardId(boardId); //좋아요 수
//                    MypageResponseDto mypageDto = new MypageResponseDto(board, likeCheck, heartsList.size(), requestDtos);
//                    //user정보, 게시글, 댓글, 좋아요 여부, 좋아요 수
//                    mypageDtoList.add(mypageDto);
//                }else{
//                    boolean likeCheck = heartRepository.existsByBoardIdAndUserId(boardId, user.getId()); //좋아요 여부
//                    List<Heart> heartsList = heartRepository.findAllByBoardId(boardId); //좋아요 수
//                    MypageResponseDto mypageDto = new MypageResponseDto(board, likeCheck, heartsList.size(), requestDtos);
//                    //user정보, 게시글, 댓글, 좋아요 여부, 좋아요 수
//                    mypageDtoList.add(mypageDto);
//                }
//            }
//        }
//        return mypageDtoList;
//    }



    //프로필 편집(사진, 소개)
    @Transactional
    public ProfileResponseDto editProfile(UserPrincipal user, MultipartFile file, String introduceMsg, Long userId) throws IOException {
        if (!userId.equals(user.getId())){
            throw new IllegalArgumentException("본인만 편집할 수 있습니다.");
        }
        User findUser = userRepository.findById(user.getId()).orElseThrow(
                ()->new IllegalArgumentException("해당 사용자가 없습니다."));
        if (!(file == null)){
            String imgUrl = s3Service.upload(file, "profile");
            ProfileRequestDto profileDto = new ProfileRequestDto(imgUrl, introduceMsg);
            findUser.updateProfile(profileDto);
            ProfileResponseDto profileResponseDto = new ProfileResponseDto(findUser);
            return profileResponseDto;
        }else{
            String imgUrl = findUser.getImgUrl();
            ProfileRequestDto profileDto = new ProfileRequestDto(imgUrl, introduceMsg);
            findUser.updateProfile(profileDto);
            ProfileResponseDto profileResponseDto = new ProfileResponseDto(findUser);
            return profileResponseDto;
        }
    }


    //닉네임 변경
    @Transactional
    public NicknameResponseDto editNick(NicknameRequestDto nickRequestDto, UserPrincipal user, Long userId) {
        if (!userId.equals(user.getId())){
            throw new IllegalArgumentException("본인만 편집할 수 있습니다.");
        }
        User findUser = userRepository.findById(user.getId()).orElseThrow(
                ()->new IllegalArgumentException("해당 사용자가 없습니다."));
        findUser.updateNick(nickRequestDto);
        NicknameResponseDto nicknameResponseDto = new NicknameResponseDto(findUser);
        return nicknameResponseDto;
    }


    //비밀번호 변경
    @Transactional
    public ResponseEntity editPwd(PasswordRequestDto pwdRequestDto, Errors errors, UserPrincipal user, Long userId) {
        if (!userId.equals(user.getId())){
            throw new IllegalArgumentException("본인만 편집할 수 있습니다.");
        }
        User findUser = userRepository.findById(user.getId()).orElseThrow(
                ()->new IllegalArgumentException("해당 사용자가 없습니다."));
        if(errors.hasErrors()){
            Map<String, String> error = userService.validateHandling(errors);
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }else{
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if (!encoder.matches(pwdRequestDto.getPwd(), findUser.getPassword())) {
                Message message = new Message("비밀번호가 틀렸습니다.");
                return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            if (!pwdRequestDto.getNewPwd().equals(pwdRequestDto.getPwdChk())) {
                Message message = new Message("비밀번호가 일치하지 않습니다.");
                return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            String newPw = bCryptPasswordEncoder.encode(pwdRequestDto.getNewPwd());
            findUser.updatePw(newPw);
            Message message = new Message("비밀번호가 변경되었습니다.");
            return new ResponseEntity<>(message, HttpStatus.OK);
        }

    }


}
