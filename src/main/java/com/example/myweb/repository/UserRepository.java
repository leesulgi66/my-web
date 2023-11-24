package com.example.myweb.repository;

import com.example.myweb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    User findByNickname(String nickName);

}

//    User findByUsernameAndPassword(String username, String password);
//    @Query(value = "SELECT * FROM member WHERE username = ?1 AND password = ?2", nativeQuery = true)
//    User login(String username, String password);   // findByUsernmaeAndPassword와 동일
