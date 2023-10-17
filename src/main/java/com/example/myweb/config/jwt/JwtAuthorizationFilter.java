package com.example.myweb.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.myweb.config.auth.PrincipalDetails;
import com.example.myweb.model.User;
import com.example.myweb.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserService userService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserService userService) {
        super(authenticationManager);
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        logger.info("JwtAuthorizationFilter : 인증 요청됨.");

        String jwtHeader = request.getHeader("Authorization");
        logger.info("Header Token : "+jwtHeader);

        // header token check
        if(jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
            chain.doFilter(request, response);
            return;
        }

        // Jwt Authorization check
        String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");

        String userId = JWT.require(Algorithm.HMAC512("secret")).build().verify(jwtToken).getClaim("id").toString();
        logger.info("user id : "+userId);

        if(userId != null) {
            System.out.println(Long.parseLong(userId));
            User userEntity = userService.findUser(Long.parseLong(userId));
            System.out.println(2);
            PrincipalDetails principalDetails = new PrincipalDetails(userEntity);
            Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}
