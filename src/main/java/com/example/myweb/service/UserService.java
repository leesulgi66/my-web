package com.example.myweb.service;

import com.example.myweb.model.Member;
import com.example.myweb.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private MemberRepository memberRepository;

    @Transactional
    public int save(Member member) {
        try{
            memberRepository.save(member);
            return 1;
        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("UserService : save() : "+e.getMessage());
        }
        return -1;
    }
}
