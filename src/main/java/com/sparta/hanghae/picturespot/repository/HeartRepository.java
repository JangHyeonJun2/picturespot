//package com.sparta.hanghae.picturespot.repository;
//
//import com.sparta.hanghae.picturespot.model.Heart;
//
//import com.sparta.hanghae.picturespot.model.User;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//
//public interface HeartRepository extends JpaRepository<Heart, Long> {
//    List<Heart> findAllByUserAndLikedOrderByModifiedDesc(User user, Boolean liked);
//
//}
