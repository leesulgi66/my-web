package com.example.myweb.chat;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(value = EnumType.STRING)
    private MessageType type;

    private String sender;

    private String roomId;

    private String message;

    @CreationTimestamp
    private Timestamp createDate;

    @ManyToOne
    @JoinColumn(name = "chatRoomId")
    private ChatRoom chatRoom;

    public enum MessageType {
        ENTER, TALK
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", type=" + type +
                ", sender='" + sender + '\'' +
                ", roomId='" + roomId + '\'' +
                ", message='" + message + '\'' +
                ", createDate=" + createDate +
                ", chatRoom=" + chatRoom +
                '}';
    }
}
