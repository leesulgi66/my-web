package com.example.myweb.chat.medel;

import com.example.myweb.model.User;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RoomMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "CHATROOM_ID")
    private ChatRoom chatRoom;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    public RoomMember(ChatRoom chatRoom, User newMember) {
        this.chatRoom = chatRoom;
        this.user = newMember;
    }
}
