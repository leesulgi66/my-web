package com.example.myweb.controller.api;

import com.example.myweb.config.auth.PrincipalDetail;
import com.example.myweb.dto.ResponseDto;
import com.example.myweb.model.Board;
import com.example.myweb.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class BoardApiController {

    @Autowired
    BoardService boardService;

    @PostMapping("/api/board")
    public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal) {
        System.out.println("BoardApiController : save 호출됨");
        boardService.boardSave(board, principal.getUser());
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }

    @DeleteMapping("/api/board/{id}")
    public ResponseDto<Integer> delete(@PathVariable Long id) {
        System.out.println("BoardApiController : delete 호출됨");
        boardService.boardDelete(id);
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }

    @PutMapping("/api/board/{id}")
    public ResponseDto<Integer> update(@PathVariable Long id, @RequestBody Board board) {
        boardService.boardUpdate(id, board);
        return new ResponseDto<>(HttpStatus.OK.value(), 1);

    }
}
