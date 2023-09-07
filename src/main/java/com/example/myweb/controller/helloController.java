package com.example.myweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class helloController {
    @GetMapping
    public String hello(Model model){
        model.addAttribute("hello","서버에서 보내준 값입니다");
        return "/index";
    }
}
