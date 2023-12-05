package com.example.myweb.service;

import com.example.myweb.dto.ReplyDto;
import com.example.myweb.model.Board;
import com.example.myweb.model.Reply;
import com.example.myweb.model.ReplyToComment;
import com.example.myweb.model.User;
import com.example.myweb.repository.BoardRepository;
import com.example.myweb.repository.ReplyRepository;
import com.example.myweb.repository.ReplyToCommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;

@Slf4j
@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private ReplyToCommentRepository replyToCommentRepository;

    @Transactional
    public void boardSave(Board board, User user) {
        board.setUser(user);
        boardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public Page<Board> boardList(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    @Transactional
    public Board boardDetail(Long id) {
         Board board = boardRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
         });

        return board;
    }

    @Transactional
    public Board boardDetail(Long id, HttpServletRequest request, HttpServletResponse response) {
        Board board = boardRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
        });

        /* 조회수 로직 */
        Calendar expireDay = Calendar.getInstance(); // 만료시간
        expireDay.add(Calendar.DAY_OF_MONTH, 1); // 다음날
        expireDay.set(Calendar.HOUR_OF_DAY, 0); // 0시
        expireDay.set(Calendar.MINUTE, 0); // 0분
        expireDay.set(Calendar.SECOND, 0); // 0초
        long count = board.getCount();
        Cookie oldCookie = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("postView")) {
                    oldCookie = cookie;
                }
            }
        }

        if (oldCookie != null) {
            if (!oldCookie.getValue().contains("["+ id +"]")) {
                board.setCount(count+1);
                oldCookie.setValue(oldCookie.getValue() + "_[" + id + "]");
                oldCookie.setPath("/");
                oldCookie.setHttpOnly(true);
                oldCookie.setMaxAge(((int) (expireDay.getTimeInMillis() - System.currentTimeMillis())) / 1000); // 쿠키 만료시간 12시 정각
                response.addCookie(oldCookie);
            }
        } else {
            board.setCount(count+1);
            Cookie newCookie = new Cookie("postView", "[" + id + "]");
            newCookie.setPath("/");
            newCookie.setHttpOnly(true);
            newCookie.setMaxAge(((int) (expireDay.getTimeInMillis() - System.currentTimeMillis())) / 1000);
            response.addCookie(newCookie);
        }
        return board;
    }

    @Transactional
    public void boardDelete(Long id, User user) {
        Board board = boardRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다.");
        });

        if(!(board.getUser().equals(user))) {
            throw new IllegalArgumentException("인증 실패 : 삭제할 수 없습니다.");
        }
        boardRepository.deleteById(id);
    }

    @Transactional
    public void boardUpdate(Long id, Board requestBoard, User user) {
        Board board = boardRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다.");
        });
        if(!(board.getUser().equals(user))) {
            throw new IllegalArgumentException("인증 실패 : 수정할 수 없습니다.");
        }
        board.setTitle(requestBoard.getTitle());
        board.setContent(requestBoard.getContent());
    }

    @Transactional
    public void replySave(Long boardId, ReplyDto requestReply, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(()->{
            return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다.");
        });

        Reply reply = new Reply();
        reply.setBoard(board);
        reply.setContent(requestReply.getContent());
        reply.setUser(user);

        replyRepository.save(reply);
    }

    public void replyDelete(Long replyId, User user) {
        Reply reply = replyRepository.findById(replyId).orElseThrow(()->{
            return new IllegalArgumentException("댓글 찾기 실패 : 아이디를 찾을 수 없습니다.");
        });
        if(!(reply.getUser().equals(user))) {
            throw new IllegalArgumentException("인증 실패 : 수정할 수 없습니다.");
        }
        replyRepository.deleteById(replyId);
    }

    @Transactional
    public void replyToCommentSave(Long boardId, ReplyDto requestReply, User user) {
        Reply reply = replyRepository.findById(boardId).orElseThrow(()->{
            return new IllegalArgumentException("댓글을 찾을 수 없습니다.");
        });

        ReplyToComment RTC = new ReplyToComment();
        RTC.setReply(reply);
        RTC.setContent(requestReply.getContent());
        RTC.setUser(user);

        replyToCommentRepository.save(RTC);
    }

    public void replyToCommentDelete(Long replyToCommentId, User user) {
        ReplyToComment reply = replyToCommentRepository.findById(replyToCommentId).orElseThrow(()->{
            return new IllegalArgumentException("댓글을 찾을 수 없습니다.");
        });
        if(!(reply.getUser().equals(user))) {
            throw new IllegalArgumentException("인증 실패 : 삭제할 수 없습니다.");
        }
        replyToCommentRepository.deleteById(replyToCommentId);
    }
}
