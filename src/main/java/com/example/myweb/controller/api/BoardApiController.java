package com.example.myweb.controller.api;

import com.example.myweb.dto.ReplyDto;
import com.example.myweb.dto.ResponseDto;
import com.example.myweb.model.Board;
import com.example.myweb.model.User;
import com.example.myweb.service.BoardService;
import com.example.myweb.service.S3Uploader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
public class BoardApiController {

    @Autowired
    BoardService boardService;

    @Autowired
    S3Uploader s3Uploader;

    @PostMapping("/api/board")
    public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal User principal) {
        log.info("BoardApiController : save 호출됨");
        boardService.boardSave(board, principal);
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }

    @DeleteMapping("/api/board/{id}")
    public ResponseDto<Integer> delete(@PathVariable Long id, @AuthenticationPrincipal User principal) {
        log.info("BoardApiController : delete 호출됨");
        boardService.boardDelete(id, principal);
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }

    @PutMapping("/api/board/{id}")
    public ResponseDto<Integer> update(@PathVariable Long id, @RequestBody Board board, @AuthenticationPrincipal User principal) {
        log.info("BoardApiController : update 호출됨");
        boardService.boardUpdate(id, board, principal);
        return new ResponseDto<>(HttpStatus.OK.value(), 1);

    }

    @PostMapping("/api/board/{boardId}/reply")
    public ResponseDto<Integer> replySave(@PathVariable Long boardId, @RequestBody ReplyDto replyDto, @AuthenticationPrincipal User principal) {
        log.info("BoardApiController : replySave 호출됨");
        boardService.replySave(boardId, replyDto, principal);
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }

    @DeleteMapping("/api/board/{boardId}/reply/{replyId}")
    public ResponseDto<Integer> replyDelete(@PathVariable Long replyId, @AuthenticationPrincipal User principal) {
        log.info("BoardApiController : replyDelete 호출됨");
        boardService.replyDelete(replyId, principal);
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }

    @PostMapping("/api/reply/{replyId}/replyToComment")
    public ResponseDto<Integer> replyToCommentSave(@PathVariable Long replyId, @RequestBody ReplyDto replyDto, @AuthenticationPrincipal User principal) {
        log.info("BoardApiController : replyToCommentSave 호출됨");
        boardService.replyToCommentSave(replyId, replyDto, principal);
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }

    @DeleteMapping("/api/reply/{replyId}/replyToComment/{replyToCommentId}")
    public ResponseDto<Integer> replyToCommentDelete(@PathVariable Long replyToCommentId, @AuthenticationPrincipal User principal) {
        log.info("BoardApiController : replyToCommentDelete 호출됨");
        boardService.replyToCommentDelete(replyToCommentId, principal);
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }

    @PostMapping("/api/board/imgUpload")
    public String imgUpload(@RequestParam("data") MultipartFile multipartFile) throws IOException, IOException {
        log.info("BoardApiController : imgUpload 호출됨");
        return s3Uploader.upload(multipartFile, "my-web");
    }
}
