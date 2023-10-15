package com.example.myweb.config.filter;

import javax.servlet.*;
import java.io.IOException;

public class Myfilter1 implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("filter 1");
        chain.doFilter(request, response); // 프로세스가 끝나지 않게 다시 필터체인으로 넘겨준다.
    }
}
