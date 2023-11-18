package com.example.myweb.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final RoomAndMessageRepository roomAndMEssageRepository;


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

    public List<RoomAndMessage> findAllMessages(Long roomId) {
        List<RoomAndMessage> messages = roomAndMEssageRepository.findAllByChatRoom(roomId);
        System.out.println(messages);
        return messages;
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
}
