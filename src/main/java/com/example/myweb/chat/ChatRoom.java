package com.example.myweb.chat;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String roomId;

    private String roomName;

    private String creator;

    @CreationTimestamp
    private Timestamp createDate;
}