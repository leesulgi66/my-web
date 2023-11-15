package com.example.myweb.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Transactional
    public void saveMessage (Message message) {
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(message.getRoomId()).orElseThrow(()->{
            throw new IllegalArgumentException("찾는 채팅 방이 없습니다.");
        });

        message.setChatRoom(chatRoom);
        messageRepository.save(message);
    }
}
