package com.example.myweb.chat.repository;

import com.example.myweb.chat.medel.ChatRoom;
import com.example.myweb.chat.medel.RoomMember;
import com.example.myweb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomMemberRepository extends JpaRepository<RoomMember, Long> {
    List<RoomMember> findAllByChatRoom(ChatRoom chatRoom);
    List<RoomMember> findAllByUser(User user);
    RoomMember findByChatRoomAndUser(ChatRoom chatRoom, User user);
    void deleteAllByChatRoom(ChatRoom chatRoom);
    void deleteAllByUser(User user);
}
