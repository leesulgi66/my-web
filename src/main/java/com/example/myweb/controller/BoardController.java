package com.example.myweb.controller;

import com.example.myweb.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BoardController {

    @Autowired
    BoardService boardService;

    @GetMapping({"","/"})
    public String index(Model model, @PageableDefault(size=10, sort="id", direction = Sort.Direction.DESC) Pageable pageable) {
        model.addAttribute("boards", boardService.boardList(pageable));
        return "index";
    }

    @GetMapping("/board/{id}")
    public String findByid(Model model,@PathVariable Long id) {
        model.addAttribute("board", boardService.boardDetail(id));
        return "board/detail";
    }

    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable Long id, Model model) {
        model.addAttribute("board", boardService.boardDetail(id));
        return "board/updateFrom";
    }

    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "/board/saveForm";
    }

    @GetMapping("/auth/S3test")
    public String S3test() {
        return "S3test";
    }
}
