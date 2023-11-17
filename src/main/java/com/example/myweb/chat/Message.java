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

    public enum MessageType {
        ENTER, TALK
    }
}
