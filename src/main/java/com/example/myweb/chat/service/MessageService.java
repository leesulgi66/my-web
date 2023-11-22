package com.example.myweb.chat.service;

import com.example.myweb.chat.medel.ChatRoom;
import com.example.myweb.chat.medel.Message;
import com.example.myweb.chat.medel.RoomAndMessage;
import com.example.myweb.chat.repository.ChatRoomRepository;
import com.example.myweb.chat.repository.MessageRepository;
import com.example.myweb.chat.repository.RoomAndMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final RoomAndMessageRepository roomAndMEssageRepository;

    @Transactional
    public void saveMessage (Message message) {
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(message.getRoomId()).orElseThrow(()->{
            throw new IllegalArgumentException("찾는 채팅 방이 없습니다.");
        });

        RoomAndMessage roomAndMessage = new RoomAndMessage(chatRoom, message);

        roomAndMEssageRepository.save(roomAndMessage);
        messageRepository.save(message);
    }
}
