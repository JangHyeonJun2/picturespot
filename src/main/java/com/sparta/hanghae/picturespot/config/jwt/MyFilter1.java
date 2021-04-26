package com.sparta.hanghae.picturespot.config.jwt;

import javax.servlet.*;
import java.io.IOException;

public class MyFilter1 implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)throws IOException, ServletException{
        System.out.println("필터1");
        chain.doFilter(request, response); // 필터에 다시 넣어줌
    }
}
