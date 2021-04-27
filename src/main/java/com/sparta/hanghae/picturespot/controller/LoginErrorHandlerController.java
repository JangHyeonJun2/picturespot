package com.sparta.hanghae.picturespot.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginErrorHandlerController implements ErrorController {

    /*
     * login시 발생하는 error를 처리하는 파일입니다.
     * */

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/error")
    public Map handleError(HttpServletRequest request){
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        Map result = new HashMap();
        result.put("message","loginfail");
        return result;
    }

}
