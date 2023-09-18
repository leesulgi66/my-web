package com.example.myweb.config.auth;

import com.example.myweb.model.User;
import com.example.myweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailService implements UserDetailsService {
    // principalDetail 에 내가 만든 User를 넣기 위한 service

    @Autowired
    private UserRepository userRepository;

    //username이 DB에 있는지 확인.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User principal = userRepository.findByUsername(username).orElseThrow(()->{
            return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다. : "+username);
        });
        return new PrincipalDetail(principal);  // spring session에 user정보 저장
    }
}
