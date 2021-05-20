//package com.sparta.hanghae.picturespot.controller;
//
//import org.assertj.core.api.Assertions;
//import org.junit.Test;
//import org.springframework.mock.env.MockEnvironment;
//import org.springframework.security.core.parameters.P;
//
//import static org.assertj.core.api.Assertions.*;
//import static org.junit.Assert.*;
//
//public class ProfileControllerUnitTest {
//    @Test
//    public void real_profile이_조회된다() {
//        //given
//        String expectedProfile = "real";
//        MockEnvironment env = new MockEnvironment();
//        env.addActiveProfile(expectedProfile);
//        env.addActiveProfile("oauth");
//        env.addActiveProfile("real-db");
//
//        ProfileController controller = new ProfileController(env);
//
//        //when
//        String profile = controller.profile();
//
//        //then
//        assertThat(profile).isEqualTo(expectedProfile);
//    }
//
//    @Test
//    public void active_profile이_없으면_default가_조회된다() {
//        //given
//        String expectedProfile = "default";
//        MockEnvironment env = new MockEnvironment();
//        ProfileController controller = new ProfileController(env);
//
//
//        //when
//        String profile = controller.profile();
//
//
//        //then
//        assertThat(profile).isEqualTo(expectedProfile);
//    }
//
//    @Test
//    public void real_profile이_없으면_첫_번째가_조회된다() {
//        //given
//        String expectedProfile = "oauth";
//        MockEnvironment env = new MockEnvironment();
//
//        env.addActiveProfile(expectedProfile);
//        env.addActiveProfile("real-db");
//
//        ProfileController controller = new ProfileController(env);
//
//        //when
//        String profile = controller.profile();
//
//        //then
//        assertThat(profile).isEqualTo(expectedProfile);
//    }
//
//}