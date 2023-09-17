package com.example.myweb.service;

import com.example.myweb.model.Member;
import com.example.myweb.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Transactional
    public void save(Member member) {
        memberRepository.save(member);
    }
}
