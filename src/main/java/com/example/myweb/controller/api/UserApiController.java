package com.example.myweb.controller.api;

import com.example.myweb.config.auth.PrincipalDetails;
import com.example.myweb.dto.ResponseDto;
import com.example.myweb.dto.UserDeleteDto;
import com.example.myweb.model.User;
import com.example.myweb.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class UserApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/auth/join")
    public ResponseDto<Integer> save(@RequestBody User user) {
        log.info("UserApiController : save 호출됨");
        userService.save(user);
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }

    @PutMapping("/user/info")
    public ResponseDto<Integer> update(@RequestBody User user, @AuthenticationPrincipal User authentication) {
        log.info("UserApiController : update 호출됨");

        userService.update(user, authentication); // 업데이트된 유저 정보를 저장
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }

    @DeleteMapping("/user/info")
    public ResponseDto<Integer> delete(@RequestBody UserDeleteDto userDeleteDto,  Authentication authentication) {
        log.info("UserApiController : delete 호출됨");
        User principalUser = (User) authentication.getPrincipal();
        userService.delete(userDeleteDto.getId(), principalUser);
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }
}
