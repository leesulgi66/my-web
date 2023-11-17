package com.example.myweb.chat;

import javax.persistence.*;

@Entity
public class RoomAndMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "CHATROOM_ID")
    private ChatRoom chatRoom;

    @ManyToOne
    @JoinColumn(name = "MESSAGE_ID")
    private Message message;

    public RoomAndMessage(ChatRoom chatRoom, Message message) {
        this.chatRoom = chatRoom;
        this.message = message;
    }
}
