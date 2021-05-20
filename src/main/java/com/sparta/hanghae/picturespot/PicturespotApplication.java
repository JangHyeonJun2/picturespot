package com.sparta.hanghae.picturespot;

import com.sparta.hanghae.picturespot.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
@EnableJpaAuditing
public class PicturespotApplication {

//    @PostConstruct
//    public void started(){
//        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
//
//    }
//
//    public static final String APPLICATION_LOCATIONS = "spring.config.location="
//            + "classpath:application-oauth2.yml,"
//            + "classpath:application.yml,"
//            + "classpath:application-aws.yml,"
//            + "classpath:application.properties";


    public static void main(String[] args) {
//        new SpringApplicationBuilder(PicturespotApplication.class)
//                .properties(APPLICATION_LOCATIONS)
//                .run(args);
        SpringApplication.run(PicturespotApplication.class, args);

    }
//    @PostConstruct
//    public void started(){
//        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
//    }
}
