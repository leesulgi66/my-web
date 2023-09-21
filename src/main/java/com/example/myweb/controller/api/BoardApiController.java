package com.example.myweb.controller.api;

import com.example.myweb.config.auth.PrincipalDetail;
import com.example.myweb.dto.ResponseDto;
import com.example.myweb.model.Board;
import com.example.myweb.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoardApiController {

    @Autowired
    BoardService boardService;

    @PostMapping("/api/board")
    public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal) {
        System.out.println("BoardApiController : 호출됨");
        boardService.BoardSave(board, principal.getUser());
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }
}
