package com.example.myweb.chat;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository <Message, Long> {
}
