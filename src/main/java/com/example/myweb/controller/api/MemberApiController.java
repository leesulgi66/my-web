package com.example.myweb.controller.api;

import com.example.myweb.dto.ResponseDto;
import com.example.myweb.model.Member;
import com.example.myweb.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberApiController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/auth/join")
    public ResponseDto<Integer> save(@RequestBody Member member) {
        System.out.println("UserApiController : save 호출됨");
        memberService.save(member);
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }
}
