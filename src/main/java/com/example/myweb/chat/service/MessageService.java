package com.example.myweb.chat.service;

import com.example.myweb.chat.medel.ChatRoom;
import com.example.myweb.chat.medel.Message;
import com.example.myweb.chat.medel.RoomAndMessage;
import com.example.myweb.chat.medel.RoomMember;
import com.example.myweb.chat.repository.ChatRoomRepository;
import com.example.myweb.chat.repository.MessageRepository;
import com.example.myweb.chat.repository.RoomAndMessageRepository;
import com.example.myweb.chat.repository.RoomMemberRepository;
import com.example.myweb.model.User;
import com.example.myweb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final RoomAndMessageRepository roomAndMEssageRepository;
    private final RoomMemberRepository roomMemberRepository;

    @Transactional
    public void saveMessage (Message message) {
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(message.getRoomId()).orElseThrow(()->{
            throw new IllegalArgumentException("찾는 채팅 방이 없습니다.");
        });

        RoomAndMessage roomAndMessage = new RoomAndMessage(chatRoom, message);

        roomAndMEssageRepository.save(roomAndMessage);
        messageRepository.save(message);
    }

    @Transactional
    public boolean saveMember (Message message) {
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(message.getRoomId()).orElseThrow(()->{
            throw new IllegalArgumentException("찾는 채팅 방이 없습니다.");
        });
        List<RoomMember> members = roomMemberRepository.findAllByChatRoom(chatRoom);
        List<Long> memberList = new ArrayList<>();
        for(RoomMember member : members) {
            memberList.add(member.getUser().getId());
        }
        if(Message.MessageType.ENTER.equals(message.getType())) {
            long senderId = message.getSenderId();
            if(memberList.contains(senderId)) {
                return false;
            }else {
                User newMember = userRepository.findById(senderId).orElseThrow(()->{
                    throw new IllegalArgumentException("해당 유저가 없습니다.");
                });
                RoomMember addMember = new RoomMember(chatRoom, newMember);
                roomMemberRepository.save(addMember);
                return true;
            }
        }
        return false;
    }
}
