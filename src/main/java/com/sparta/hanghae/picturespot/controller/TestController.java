package com.sparta.hanghae.picturespot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/index")
    public String hello(){
        return "index";
    }
}
