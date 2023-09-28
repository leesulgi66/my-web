package com.example.myweb.controller.api;

import com.example.myweb.dto.ResponseDto;
import com.example.myweb.model.User;
import com.example.myweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApiController {

    @Autowired
    private UserService userService;

    @PostMapping("/auth/join")
    public ResponseDto<Integer> save(@RequestBody User user) {
        System.out.println("UserApiController : save 호출됨");
        userService.save(user);
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }

    @PutMapping("/user/info")
    public ResponseDto<Integer> update(@RequestBody User user) {
        userService.update(user);
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }
}
