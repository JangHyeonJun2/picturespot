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
    public static final String APPLICATION_LOCATIONS = "spring.config.location="
            + "/home/ec2-user/app/application-oauth2.yml,"
            + "classpath:application.yml,"
            + "/home/ec2-user/app/application-aws.yml,"
            + "/home/ec2-user/app/application-mail.yml,"
            + "/home/ec2-user/app/application-db.yml";


    public static void main(String[] args) {
        new SpringApplicationBuilder(PicturespotApplication.class)
                .properties(APPLICATION_LOCATIONS)
                .run(args);
        SpringApplication.run(PicturespotApplication.class, args);

    }
//    @PostConstruct
//    public void started(){
//        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
//    }
}
