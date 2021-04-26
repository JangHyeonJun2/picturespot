package com.sparta.hanghae.picturespot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PicturespotApplication {

    public static void main(String[] args) {
        SpringApplication.run(PicturespotApplication.class, args);
    }

}
