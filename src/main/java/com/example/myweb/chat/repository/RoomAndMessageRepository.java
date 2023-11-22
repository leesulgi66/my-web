package com.example.myweb.chat.repository;

import com.example.myweb.chat.medel.ChatRoom;
import com.example.myweb.chat.medel.RoomAndMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomAndMessageRepository extends JpaRepository <RoomAndMessage, Long> {
    List<RoomAndMessage> findAllByChatRoom(ChatRoom chatRoom);
}
