package com.example.myweb.repository;

import com.example.myweb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}

//    Member findByUsernameAndPassword(String username, String password);
//    @Query(value = "SELECT * FROM member WHERE username = ?1 AND password = ?2", nativeQuery = true)
//    Member login(String username, String password);   // findByUsernmaeAndPassword와 동일
