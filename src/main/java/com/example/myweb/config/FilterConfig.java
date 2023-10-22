//package com.example.myweb.config;
//
//import com.example.myweb.config.filter.Myfilter1;
//import com.example.myweb.config.filter.Myfilter2;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class FilterConfig {
//
//    @Bean
//    public FilterRegistrationBean<Myfilter1> filter1() {
//        FilterRegistrationBean<Myfilter1> bean = new FilterRegistrationBean<>(new Myfilter1());
//        bean.addUrlPatterns("/*");
//        bean.setOrder(1); // 낮은 번호가 필터중 가장 먼저 실행됨 하지만, security filter가 모두 실행되고 난 후 실행이 됨.
//        return bean;
//    }
//
//    @Bean
//    public FilterRegistrationBean<Myfilter2> filter2() {
//        FilterRegistrationBean<Myfilter2> bean = new FilterRegistrationBean<>(new Myfilter2());
//        bean.addUrlPatterns("/*");
//        bean.setOrder(0); // 낮은 번호가 필터중 가장 먼저 실행됨
//        return bean;
//    }
//}
