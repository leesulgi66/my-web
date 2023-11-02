package com.example.myweb.chat;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ChatRoom {
    private String roomId;
    private String name;

    @Builder
    public static ChatRoom create(String name) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.name = name;
        return chatRoom;
    }
}