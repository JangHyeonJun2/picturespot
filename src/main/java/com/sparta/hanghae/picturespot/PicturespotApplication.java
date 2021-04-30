package com.sparta.hanghae.picturespot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PicturespotApplication {

    public static final String APPLICATION_LOCATIONS = "spring.config.location="
            + "classpath:oauth2.yml,"
            + "classpath:application.properties";

    public static void main(String[] args) {
        new SpringApplicationBuilder(PicturespotApplication.class)
                .properties(APPLICATION_LOCATIONS)
                .run(args);
    }

}
