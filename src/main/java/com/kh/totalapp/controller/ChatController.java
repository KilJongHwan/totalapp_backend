package com.kh.totalapp.controller;

import com.kh.totalapp.dto.ChatMessageDTO;
import com.kh.totalapp.dto.ChatRoomReqDTO;
import com.kh.totalapp.dto.ChatRoomResDTO;
import com.kh.totalapp.entity.Chat;
import com.kh.totalapp.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kh.totalapp.utils.Common.CORS_ORIGIN;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = CORS_ORIGIN)
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;
    @PostMapping("/new")
    public ResponseEntity<String> createRoom(@RequestBody ChatRoomReqDTO chatRoomReqDTO) {
        log.warn("chatRoomDto : {}", chatRoomReqDTO);
        ChatRoomResDTO room = chatService.createRoom(chatRoomReqDTO.getName());
        System.out.println(room.getRoomId());
        return new ResponseEntity<>(room.getRoomId(), HttpStatus.OK);
    }
    @GetMapping("/list")
    public List<ChatRoomResDTO> findAllRoom() {
        return chatService.findAllRoom();
    }
    // 방 정보 가져오기
    @GetMapping("/room/{roomId}")
    public ChatRoomResDTO findRoomById(@PathVariable String roomId) {
        return chatService.findRoomById(roomId);
    }
    @PostMapping("/message")
    public ResponseEntity<Void> saveMessage(@RequestBody ChatMessageDTO chatMessageDTO) {
        chatService.saveMessage(chatMessageDTO.getRoomId(), chatMessageDTO.getSender(), chatMessageDTO.getMessage());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/message/{roomId}")
    public List<Chat> getRecentMessages(@PathVariable String roomId) {
        return chatService.getRecentMessages(roomId);
    }
}
