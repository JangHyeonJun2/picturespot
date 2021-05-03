//package com.sparta.hanghae.picturespot.dto;
//
//import com.sparta.hanghae.picturespot.model.Board;
//import com.sparta.hanghae.picturespot.model.User;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Getter
//@NoArgsConstructor
//public class BoardDto {
//
//    //----좋아요, 유저 정보 추가해야함----//
//
//    //private Long id;
//    private String title;
//    private String content;
//    private String category;
//    private String imgUrl;
//    private BigDecimal latitude;
//    private BigDecimal longitude;
//    private int likeCount;
//    //private Long userId;
//    private String writer;
//
//    private List<CommentDto> commentDtos;
//
//    private LocalDateTime modified;
//
//    private UserDto userDto;
//
//
//    public BoardDto(Board board, List<CommentDto> commentDtos, UserDto userDto){
//        //this.id = board.getId();
//        this.title = board.getTitle();
//        this.content = board.getContent();
//        this.category = board.getCategory();
//        this.imgUrl = board.getImgUrl();
//        this.latitude = board.getLatitude();
//        this.longitude = board.getLongitude();
//        this.likeCount = board.getLikeCount();
//        //this.userId = board.getUser().getId();
//        this.writer = board.getUser().getNickname();
//        this.modified = board.getModified();
//        this.commentDtos = commentDtos;
//
//        this.userDto = userDto;
//    }
//
//
//}
