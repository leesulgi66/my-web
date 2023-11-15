package com.example.myweb.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessageSendingOperations simpMessageSendingOperations;
    private final MessageService messageService;

    /*
        /sub/channel/12345      - 구독(channelId:12345)
        /pub//chat/message              - 메시지 발행
    */

    @MessageMapping("/chat/message")
    public void message(Message message) {
        if(Message.MessageType.ENTER.equals(message.getType())){
            message.setMessage(message.getSender()+"님이 입장했습니다.");
        }
        messageService.saveMessage(message);
        simpMessageSendingOperations.convertAndSend("/sub/channel/" + message.getRoomId(), message);
    }
}
