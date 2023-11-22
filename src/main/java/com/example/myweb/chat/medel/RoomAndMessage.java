package com.example.myweb.chat.medel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
