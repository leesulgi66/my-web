package com.example.myweb.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/auth/joinForm")
    public String joinForm() {

        return "user/joinForm";
    }

    @GetMapping("/auth/loginForm")
    public String loginFrom() {

        return "user/loginForm";
    }

    @GetMapping("/user/info")
    public String userInfo() {

        return "user/info";
    }
}
