package com.example.myweb.chat;

import com.example.myweb.model.Reply;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

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

    @OneToMany(mappedBy = "chatRoom" ,cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties({"chatRoom"}) // 무한참조 방지
    @OrderBy("id desc")
    private List<RoomAndMessage> rooms;

    @CreationTimestamp
    private Timestamp createDate;
}