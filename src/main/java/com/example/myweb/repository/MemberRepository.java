package com.example.myweb.repository;

import com.example.myweb.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByUsernameAndPassword(String username, String password);
//    @Query(value = "SELECT * FROM member WHERE username = ?1 AND password = ?2", nativeQuery = true)
//    Member login(String username, String password);   // findByUsernmaeAndPassword와 동일
}
