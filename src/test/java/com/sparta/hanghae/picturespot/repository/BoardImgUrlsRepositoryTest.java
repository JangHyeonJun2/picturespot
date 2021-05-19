package com.sparta.hanghae.picturespot.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BoardImgUrlsRepositoryTest {
    @Autowired
    BoardImgUrlsRepository boardImgUrlsRepository;
    @Test
    public void 이미지삭제() {
//        boardImgUrlsRepository.deleteById(137L);
    }

}