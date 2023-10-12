package com.example.myweb.service;

import com.example.myweb.config.auth.PrincipalDetails;
import com.example.myweb.model.User;
import com.example.myweb.repository.BoardRepository;
import com.example.myweb.repository.ReplyRepository;
import com.example.myweb.repository.ReplyToCommentRepository;
import com.example.myweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;


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
    public User OauthUserSave(String provider, OAuth2User oAuth2User) {
        String providerId = oAuth2User.getAttribute("sub");
        String username = "social"+provider+"_"+providerId;
        String nickname = oAuth2User.getAttribute("name");
        String password = passwordEncoder.encode(provider+providerId);
        String providerEmail = oAuth2User.getAttribute("email");
        String providerProfileImage = oAuth2User.getAttribute("picture");
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isEmpty()) {
            User OauthUser = User.builder()
                .username(username)
                .nickname(nickname)
                .password(password)
                .email(providerEmail)
                .profileImage(providerProfileImage)
                .role(User.Role.USER)
                .build();
            userRepository.save(OauthUser);
            return OauthUser;
        }else {
            return user.get();
        }
    }

    @Transactional
    public void update(User user, PrincipalDetails principal) {
        User persistence = userRepository.findById(user.getId()).orElseThrow(()->{
            return new IllegalArgumentException("회원 찾기 실패");
        });
        if(!Objects.equals(persistence.getUsername(), principal.getUsername())) {
            throw new IllegalArgumentException("인증 실패");
        }
        String rawPassword = user.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);
        persistence.setPassword(encPassword);
        persistence.setNickname(user.getNickname());
        persistence.setEmail(user.getEmail());
        persistence.setProfileImage(user.getProfileImage());
        principal.setUser(persistence);
    }

    @Transactional
    public void delete(Long id, PrincipalDetails principal) {
        User delUser = userRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("회원 탈퇴 실패 : 찾는 아이디가 없습니다.");
        });

        if(!Objects.equals(delUser.getUsername(), principal.getUsername())) {
            throw new IllegalArgumentException("인증 실패");
        }

        replyToCommentRepository.deleteAllByUserId(delUser.getId());
        replyRepository.deleteAllByUserId(delUser.getId());
        boardRepository.deleteAllByUserId(delUser.getId());
        userRepository.delete(delUser);
    }
}
