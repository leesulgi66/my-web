package com.example.myweb.controller.api;

import com.example.myweb.dto.ResponseDto;
import com.example.myweb.model.Member;
import com.example.myweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Arrays;

@RestController
public class UserApiController {

    @Autowired
    private UserService userService;

    @PostMapping("/api/user")
    public ResponseDto<Integer> save(@RequestBody Member member) {
        System.out.println("UserApiController : save 호출됨");
        member.setRole(Member.Role.USER);
        userService.save(member);
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }

    @PostMapping("/api/user/login")
    public ResponseDto<Integer> login(@RequestBody Member member, HttpSession session) {
        System.out.println("UserApiController : login 호출됨");
        Member principal = userService.login(member);

        if(principal != null) {
            session.setAttribute("principal", principal);
        }

        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }
}
