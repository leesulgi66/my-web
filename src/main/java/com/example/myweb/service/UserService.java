package com.example.myweb.service;

import com.example.myweb.chat.repository.RoomMemberRepository;
import com.example.myweb.chat.service.ChatRoomService;
import com.example.myweb.config.oauth.OAuth2UserInfo;
import com.example.myweb.model.User;
import com.example.myweb.repository.BoardRepository;
import com.example.myweb.repository.ReplyRepository;
import com.example.myweb.repository.ReplyToCommentRepository;
import com.example.myweb.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private ReplyRepository replyRepository;
    @Autowired
    private ChatRoomService chatRoomService;
    @Autowired
    private RoomMemberRepository roomMemberRepository;
    @Autowired
    private ReplyToCommentRepository replyToCommentRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public User findUser(long id) {
        User user = userRepository.findById(id).orElseThrow(()->{
            throw new IllegalArgumentException("회원 찾기 실패 : 찾는 아이디가 없습니다.");
        });

        return user;
    }

    @Transactional
    public User findUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(()->{
            throw new IllegalArgumentException("회원 찾기 실패 : 찾는 아이디가 없습니다.");
        });

        return user;
    }

    @Transactional
    public void save(User user) {
        String rawPassword = user.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        user.setRole(User.Role.ROLE_USER);
        userRepository.save(user);
    }

    @Transactional
    public User OauthUserSave(OAuth2UserInfo oAuth2User) {
        String provider = oAuth2User.getProvider();
        String providerId = oAuth2User.getProviderId();
        String username = "_social_"+provider+"_"+providerId;
        String nickname = oAuth2User.getNickname();
        String password = passwordEncoder.encode(provider+providerId);
        String providerEmail = oAuth2User.getEmail();
        String providerProfileImage = oAuth2User.getProfileImage();
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isEmpty()) {
            log.info("자동 회원가입");
            User OauthUser = User.builder()
                .username(username)
                .nickname(nickname)
                .password(password)
                .email(providerEmail)
                .profileImage(providerProfileImage)
                .provider(provider)
                .role(User.Role.ROLE_USER)
                .build();
            userRepository.save(OauthUser);
            return OauthUser;
        }else {
            return user.get();
        }
    }

    @Transactional
    public void update(User user, User principal) {
        User persistence = userRepository.findById(user.getId()).orElseThrow(()->{
            return new IllegalArgumentException("회원 찾기 실패 : 찾는 아이디가 없습니다.");
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

        principal.setPassword(encPassword);
        principal.setNickname(user.getNickname());
        principal.setEmail(user.getEmail());
        principal.setProfileImage(user.getProfileImage());
    }

    @Transactional
    public void delete(Long id, User principal) {
        User delUser = userRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("회원 탈퇴 실패 : 찾는 아이디가 없습니다.");
        });

        if(!Objects.equals(delUser.getUsername(), principal.getUsername())) {
            throw new IllegalArgumentException("인증 실패");
        }

        chatRoomService.deleteRoom(delUser);
        roomMemberRepository.deleteAllByUser(delUser);
        replyToCommentRepository.deleteAllByUserId(delUser.getId());
        replyRepository.deleteAllByUserId(delUser.getId());
        boardRepository.deleteAllByUserId(delUser.getId());
        userRepository.delete(delUser);
    }

    @Transactional
    public void refreshSave(long userId, String refresh) {
        User user = userRepository.findById(userId).orElseThrow(()->{
            return new IllegalArgumentException("회원 탈퇴 실패 : 찾는 아이디가 없습니다.");
        });

        user.setRefresh(refresh);
    }
}
