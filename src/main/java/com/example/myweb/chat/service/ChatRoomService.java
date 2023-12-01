package com.example.myweb.chat.service;

import com.example.myweb.chat.medel.ChatRoom;
import com.example.myweb.chat.medel.Message;
import com.example.myweb.chat.medel.RoomAndMessage;
import com.example.myweb.chat.medel.RoomMember;
import com.example.myweb.chat.repository.ChatRoomRepository;
import com.example.myweb.chat.repository.RoomAndMessageRepository;
import com.example.myweb.chat.repository.RoomMemberRepository;
import com.example.myweb.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final RoomAndMessageRepository roomAndMEssageRepository;
    private final RoomMemberRepository roomMemberRepository;


    public List<ChatRoom> findAllRoom() {
        // 채팅방 생성순서 최근 순으로 반환
        List chatRooms = chatRoomRepository.findAll();
        Collections.reverse(chatRooms);
        return chatRooms;
    }

    public ChatRoom findRoomById(String id) {
        return chatRoomRepository.findByRoomId(id).orElseThrow(()->{
            throw new RuntimeException("찾는 채팅방이 없습니다.");
        });
    }

    public List<Message> findAllMessages(ChatRoom chatRoom) {
        List<RoomAndMessage> messages = roomAndMEssageRepository.findAllByChatRoom(chatRoom);
        List<Message> messageList = new ArrayList<>();
        for(RoomAndMessage message : messages) {
            messageList.add(message.getMessage());
        }
        return messageList;
    }

    // service
    public ChatRoom createChatRoom(String name, String creator) {
        ChatRoom chatRoom = ChatRoom.builder()
                        .roomId(UUID.randomUUID().toString())
                        .creator(creator)
                        .roomName(name)
                        .build();
        chatRoomRepository.save(chatRoom);
        return chatRoom;
    }

    @Transactional
    public void outRoom(String roomId, User user) {
        ChatRoom outRoom = chatRoomRepository.findByRoomId(roomId).orElseThrow(()->{
            throw new IllegalArgumentException("찾는 채팅 방이 존재하지 않습니다.");
        });

        RoomMember roomAndMember = roomMemberRepository.findByChatRoomAndUser(outRoom, user);
        roomMemberRepository.delete(roomAndMember);
    }

    @Transactional
    public void deleteRoom(String roomId, User user) {
        ChatRoom delRoom = chatRoomRepository.findByRoomId(roomId).orElseThrow(()->{
            throw new IllegalArgumentException("찾는 채팅 방이 존재하지 않습니다.");
        });

        if(delRoom.getCreator().equals(user.getNickname())) {
            roomMemberRepository.deleteAllByChatRoom(delRoom);
            roomAndMEssageRepository.deleteAllByChatRoom(delRoom);
            chatRoomRepository.deleteById(delRoom.getId());
        }
    }

    @Transactional
    public void deleteRoom(User user) {
        List<RoomMember> delRooms = roomMemberRepository.findAllByUser(user);

        for(RoomMember delRoom : delRooms) {
            deleteRoom(delRoom.getChatRoom().getRoomId(), user);
        }
    }
}
