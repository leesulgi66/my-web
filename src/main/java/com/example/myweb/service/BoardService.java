package com.example.myweb.service;

import com.example.myweb.model.Board;
import com.example.myweb.model.User;
import com.example.myweb.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Transactional
    public void BoardSave(Board board, User user) {
        board.setUser(user);
        boardRepository.save(board);

    }
}
