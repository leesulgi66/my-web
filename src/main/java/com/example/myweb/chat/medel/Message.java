package com.example.myweb.chat.medel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

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
