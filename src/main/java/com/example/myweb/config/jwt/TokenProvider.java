package com.example.myweb.config.jwt;

import com.example.myweb.config.auth.PrincipalDetails;
import com.example.myweb.model.User;
import com.example.myweb.service.UserService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider implements InitializingBean {

    private static final String AUTHORITIES_KEY = "auth";
    private final String secret;
    private final long tokenValidityInMilliseconds;
    private Key key;
    private final UserService userService;


    public TokenProvider(@Value("${jwt.secret}") String secret, @Value("${jwt.token.validity-in-seconds}") long tokenValidityInMilliseconds,
                         UserService userService) {
        this.userService = userService;
        this.secret = secret;
        this.tokenValidityInMilliseconds = tokenValidityInMilliseconds;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // Bean이 생성이 되고 DI 받은 후 주입받은 secret 값을 Base64로 decode 해서 key변수 할당
        byte[] keyBytes =  Base64.getDecoder().decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(Authentication authentication, PrincipalDetails principalDetails){
        long userId = principalDetails.getUser().getId();
        String userAuthority = principalDetails.getUser().getRole().toString();

        long now = (new Date().getTime());
        Date validity = new Date(now + this.tokenValidityInMilliseconds);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, userAuthority)
                .claim("id", userId)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    public String createToken(User principalDetails){
        long userId = principalDetails.getId();
        String userAuthority = principalDetails.getRole().toString();

        long now = (new Date().getTime());
        Date validity = new Date(now + this.tokenValidityInMilliseconds);

        return Jwts.builder()
                .setSubject("refresh")
                .claim(AUTHORITIES_KEY, userAuthority)
                .claim("id", userId)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    public String createRefreshToken(PrincipalDetails principalDetails){
        long userId = principalDetails.getUser().getId();
        String userAuthority = principalDetails.getUser().getRole().toString();

        long now = (new Date().getTime());
        Date validity = new Date((now+this.tokenValidityInMilliseconds)*10);

        return Jwts.builder()
                .setSubject("RefreshToken")
                .claim(AUTHORITIES_KEY, userAuthority)
                .claim("id", userId)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        long userId = Long.parseLong(claims.get("id").toString());

        User principalUser = userService.findUser(userId);

        return new UsernamePasswordAuthenticationToken(principalUser, token, authorities);
    }

    //토큰을 받아서 유효성 검사
    public JwtCode validateToken(String token) {
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return JwtCode.ACCESS;
        } catch (io.jsonwebtoken.security.SignatureException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e ){
            log.info("만료된 JWT 서명입니다.");
            return JwtCode.EXPIRED;
        } catch (UnsupportedJwtException e ){
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e ) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return JwtCode.DENIED;
    }

    public String getRefresh(String token) {
        Base64.Decoder decoder = Base64.getDecoder();
        String[] jwtPayload = token.split("\\.");
        byte[] decodedBytes = decoder.decode(jwtPayload[1].getBytes());
        String tokenString = new String(decodedBytes);

        long id = Long.parseLong(tokenString.split(",")[2].split(":")[1].replace("\"",""));

        String refresh = userService.findUser(id).getRefresh();

        return refresh;
    }

    public static enum JwtCode{
        DENIED,
        ACCESS,
        EXPIRED;
    }
}
