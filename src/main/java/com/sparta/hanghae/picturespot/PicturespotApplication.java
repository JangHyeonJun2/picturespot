package com.sparta.hanghae.picturespot;

import com.sparta.hanghae.picturespot.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
//@EnableCaching
@EnableConfigurationProperties(AppProperties.class)
@EnableJpaAuditing
public class PicturespotApplication {

//    @PostConstruct
//    public void started(){
//        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
//
//    }
//
    //main 실행문
    public static final String APPLICATION_LOCATIONS = "spring.config.location="
            + "classpath:application.yml,"
//            + "classpath:application-real.yml,"
//            + "classpath:application-credentials.yml,"
            + "classpath:application-oauth2.yml,"
            + "classpath:application-mail.yml,"
//            + "classpath:application-aws.yml,"
            + "classpath:application-db.yml";


    public static void main(String[] args) {
        new SpringApplicationBuilder(PicturespotApplication.class)
                .properties(APPLICATION_LOCATIONS)
                .run(args);
//        SpringApplication.run(PicturespotApplication.class, args);
    }
//    @PostConstruct
//    public void started(){
//        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
//    }
}
