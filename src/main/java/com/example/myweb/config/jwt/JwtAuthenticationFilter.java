//package com.example.myweb.config.jwt;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.example.myweb.config.auth.PrincipalDetails;
//import com.example.myweb.model.User;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.util.Date;
//
//@Slf4j
//public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
//        super(authenticationManager);
//    }
//
//    // /login 요청을 하면 로그인 시도를 위해서 실행되는 함수
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        log.info("JwtAuthenticationFilter : 로그인 요청");
//
//        try {
////            // 요청온 데이터를 파싱
////            BufferedReader br = request.getReader();
////            String input = null;
////            while ((input = br.readLine()) != null) {
////                log.info(input);
////            }
//            ObjectMapper om = new ObjectMapper(); // 입력값을 object로 받는법.
//            User user = om.readValue(request.getInputStream(), User.class);
////            log.info(String.valueOf(user));  // 입력값 확인
//
//            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
//
//            // AuthenticationManager 실행시 PrincipalDetailsService 의 loadUserByUsername() 실행됨.
//            Authentication authentication = getAuthenticationManager().authenticate(authenticationToken);
//
//            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
//
//            log.info("login username : {}",principalDetails.getUsername());
//
//            return authentication; // authentication을 return하면 세션에 정보가 저장됨. jwt는 세션에 저장 할 필요는 없지만, 권한 처리 때문에 저장해줌.
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    // 위의 attemptAuthentication 실행 후 인증이 정상적으로 되었으면 아래의 successfulAuthentication 함수가 실행됨.
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
//        log.info("successfulAuthentication : 인증 완료");
//
//        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
//
//        String jwtToken = JWT.create()
//                .withSubject(principalDetails.getUsername()) // token name
//                .withExpiresAt(new Date(System.currentTimeMillis()+(1000*60*10)))
//                .withClaim("id", principalDetails.getUser().getId())
//                .sign(Algorithm.HMAC512("secret"));
//
//        response.addHeader("Authorization", "Bearer "+jwtToken);
//    }
//}
