package com.example.myweb.controller.api;

import com.example.myweb.config.auth.PrincipalDetail;
import com.example.myweb.dto.ReplyDto;
import com.example.myweb.dto.ResponseDto;
import com.example.myweb.model.Board;
import com.example.myweb.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class BoardApiController {

    @Autowired
    BoardService boardService;

    @PostMapping("/api/board")
    public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal) {
        log.info("BoardApiController : save 호출됨");
        boardService.boardSave(board, principal.getUser());
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }

    @DeleteMapping("/api/board/{id}")
    public ResponseDto<Integer> delete(@PathVariable Long id) {
        log.info("BoardApiController : delete 호출됨");
        boardService.boardDelete(id);
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }

    @PutMapping("/api/board/{id}")
    public ResponseDto<Integer> update(@PathVariable Long id, @RequestBody Board board) {
        log.info("BoardApiController : update 호출됨");
        boardService.boardUpdate(id, board);
        return new ResponseDto<>(HttpStatus.OK.value(), 1);

    }

    @PostMapping("/api/board/{boardId}/reply")
    public ResponseDto<Integer> replySave(@PathVariable Long boardId, @RequestBody ReplyDto replyDto, @AuthenticationPrincipal PrincipalDetail principal) {
        log.info("BoardApiController : replySave 호출됨");
        boardService.replySave(boardId, replyDto, principal.getUser());
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }

    @DeleteMapping("/api/board/{boardId}/reply/{replyId}")
    public ResponseDto<Integer> replyDelete(@PathVariable Long replyId) {
        log.info("BoardApiController : replyDelete 호출됨");
        boardService.replyDelete(replyId);
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }

    @PostMapping("/api/board/{boardId}/reply")
    public ResponseDto<Integer> replyToCommentSave(@PathVariable Long replyId, @RequestBody ReplyDto replyDto, @AuthenticationPrincipal PrincipalDetail principal) {
        log.info("BoardApiController : replySave 호출됨");
        boardService.replyToCommentSave(replyId, replyDto, principal.getUser());
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }
}
