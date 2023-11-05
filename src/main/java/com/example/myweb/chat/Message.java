package com.example.myweb.chat;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    public enum MessageType {
        ENTER, TALK
    }
    private MessageType type;
    private String sender;
    private String channelId;
    private String message;
}
