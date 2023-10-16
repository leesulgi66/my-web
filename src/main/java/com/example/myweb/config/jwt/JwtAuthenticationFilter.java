package com.example.myweb.config.jwt;

import com.example.myweb.config.auth.PrincipalDetails;
import com.example.myweb.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    // /login 요청을 하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("JwtAuthenticationFilter : 로그인 요청");

        try {
//            // 요청온 데이터를 파싱
//            BufferedReader br = request.getReader();
//            String input = null;
//            while ((input = br.readLine()) != null) {
//                log.info(input);
//            }
            ObjectMapper om = new ObjectMapper(); // 입력값을 object로 받는법.
            User user = om.readValue(request.getInputStream(), User.class);
//            log.info(String.valueOf(user));  // 입력값 확인

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

            // AuthenticationManager 실행시 PrincipalDetailsService 의 loadUserByUsername() 실행됨.
            Authentication authentication = getAuthenticationManager().authenticate(authenticationToken);

            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

            log.info("login username : {}",principalDetails.getUsername());

            return authentication; // authentication을 return하면 세션에 정보가 저장됨. jwt는 세션에 저장 할 필요는 없지만, 권한 처리 때문에 저장해줌.
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
