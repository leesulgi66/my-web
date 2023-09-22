package com.example.myweb.controller;

import com.example.myweb.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @Autowired
    BoardService boardService;

    @GetMapping({"","/"})
    public String index(Model model) {
        model.addAttribute("boards", boardService.boardList());
        return "index";
    }

    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "/board/saveForm";
    }
}
