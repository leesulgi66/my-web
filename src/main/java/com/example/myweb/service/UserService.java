package com.example.myweb.service;

import com.example.myweb.config.auth.PrincipalDetail;
import com.example.myweb.model.User;
import com.example.myweb.repository.BoardRepository;
import com.example.myweb.repository.ReplyRepository;
import com.example.myweb.repository.ReplyToCommentRepository;
import com.example.myweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private ReplyRepository replyRepository;
    @Autowired
    private ReplyToCommentRepository replyToCommentRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public void save(User user) {
        String rawPassword = user.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        user.setRole(User.Role.USER);
        userRepository.save(user);
    }

    @Transactional
    public void update(User user, PrincipalDetail principal) {
        User persistence = userRepository.findById(user.getId()).orElseThrow(()->{
            return new IllegalArgumentException("회원 찾기 실패");
        });
        String rawPassword = user.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);
        persistence.setPassword(encPassword);
        persistence.setNickname(user.getNickname());
        persistence.setEmail(user.getEmail());
        principal.setUser(persistence);
    }

    @Transactional
    public void delete(Long id) {
        User delUser = userRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("회원 탈퇴 실패 : 찾는 아이디가 없습니다.");
        });
//        replyToCommentRepository.deleteAllByUserId(delUser.getId());
//        replyRepository.deleteAllByUserId(delUser.getId());
//        boardRepository.deleteAllByUserId(delUser.getId());
        userRepository.delete(delUser);
    }
}
