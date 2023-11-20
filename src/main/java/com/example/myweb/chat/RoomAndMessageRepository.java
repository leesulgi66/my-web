package com.example.myweb.chat;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomAndMessageRepository extends JpaRepository <RoomAndMessage, Long> {
    List<RoomAndMessage> findAllByChatRoom(ChatRoom chatRoom);
}
