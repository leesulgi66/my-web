package com.example.myweb.chat.medel;

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

    @OneToMany(mappedBy = "chatRoom", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties({"chatRoom"})
    @OrderBy("id desc")
    private List<RoomMember> members;

    @CreationTimestamp
    private Timestamp createDate;
}