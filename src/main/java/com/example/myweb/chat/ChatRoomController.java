package com.example.myweb.chat;

import com.example.myweb.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    // 뷰 페이지
    // 채팅 리스트 화면
    @GetMapping("/room")
    public String rooms(Model model) {
        List<ChatRoom> rooms = chatRoomService.findAllRoom();
        model.addAttribute("rooms", rooms);
        return "/chat/room";
    }
    // 채팅방 입장 화면
    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId) {
        ChatRoom chatRoom = chatRoomService.findRoomById(roomId);
        model.addAttribute("roomName", chatRoom.getRoomName());
        model.addAttribute("roomId", roomId);
        return "/chat/roomdetail";
    }

    //api 요청
    // 모든 채팅방 목록 반환
    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> room() {
        return chatRoomService.findAllRoom();
    }
    // 채팅방 생성
    @PostMapping("/room")
    @ResponseBody
    public ChatRoom createRoom(@RequestBody Map<String, String> roomName, @AuthenticationPrincipal User user) {
        return chatRoomService.createChatRoom(roomName.get("name"), user.getNickname());
    }
    // 특정 채팅방 조회
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable String roomId) {
        return chatRoomService.findRoomById(roomId);
    }
}
