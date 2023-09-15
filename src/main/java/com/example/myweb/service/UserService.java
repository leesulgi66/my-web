package com.example.myweb.service;

import com.example.myweb.model.Member;
import com.example.myweb.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {

    @Autowired
    private MemberRepository memberRepository;

    @Transactional
    public void save(Member member) {
        memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public Member login(Member member) {
        return memberRepository.findByUsernameAndPassword(member.getUsername(), member.getPassword());
    }
}
