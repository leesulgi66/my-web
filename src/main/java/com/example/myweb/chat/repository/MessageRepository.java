package com.example.myweb.chat.repository;

import com.example.myweb.chat.medel.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository <Message, Long> {
}
