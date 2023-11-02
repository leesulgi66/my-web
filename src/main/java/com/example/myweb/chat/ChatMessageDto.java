package com.example.myweb.chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDto {
    public enum MessageType{
        ENTER,JOIN,TALK
    }
    private MessageType type;
    private String roomId;
    private String sender;
    private String message;
}
