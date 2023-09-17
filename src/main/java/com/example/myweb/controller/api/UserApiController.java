package com.example.myweb.controller.api;

import com.example.myweb.dto.ResponseDto;
import com.example.myweb.model.User;
import com.example.myweb.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApiController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/auth/join")
    public ResponseDto<Integer> save(@RequestBody User user) {
        System.out.println("UserApiController : save 호출됨");
        memberService.save(user);
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }
}
