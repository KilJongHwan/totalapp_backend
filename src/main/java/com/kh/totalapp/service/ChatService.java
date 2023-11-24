package com.kh.totalapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.totalapp.dto.ChatRoomResDTO;
import com.kh.totalapp.entity.Chat;
import com.kh.totalapp.entity.ChatRoom;
import com.kh.totalapp.repository.ChatRepository;
import com.kh.totalapp.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {
    private final ObjectMapper objectMapper;
    private Map<String, ChatRoomResDTO> chatRooms;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;

    @PostConstruct // 의존성 주입 이후 초기화를 수행
    private void init(){
        chatRooms = new LinkedHashMap<>();
    }
    public List<ChatRoomResDTO> findAllRoom() {
        return new ArrayList<>(chatRooms.values());
    }

    public ChatRoomResDTO findRoomById(String roomId) {
        return chatRooms.get(roomId);
    }
    // 방 개설하기
    public ChatRoomResDTO createRoom(String name) {
        String randomId = UUID.randomUUID().toString();
        log.info("UUID : " + randomId);
        ChatRoomResDTO chatRoom = ChatRoomResDTO.builder()
                .roomId(randomId)
                .name(name)
                .regDate(LocalDateTime.now())
                .build();
        ChatRoom chatRoomEntity = new ChatRoom();
        chatRoomEntity.setRoomId(randomId);
        chatRoomEntity.setRoomName(name);
        chatRoomEntity.setCreatedAt(LocalDateTime.now());
        chatRoomRepository.save(chatRoomEntity);
        chatRooms.put(randomId, chatRoom);
        return chatRoom;
    }
    public void removeRoom(String roomId) {
        ChatRoomResDTO room = chatRooms.get(roomId);
        if (room != null) {
            if (room.isSessionEmpty()) {
                chatRooms.remove(roomId);
                ChatRoom chatRoomEntity = chatRoomRepository.findById(roomId).orElseThrow(
                        () -> new RuntimeException("해당 채팅방이 존재하지 않습니다.")
                );
                if (chatRoomEntity != null) {
                    chatRoomRepository.delete(chatRoomEntity);
                }
            }
        }
    }
    public <T> void sendMessage(WebSocketSession session, T message){
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }
    public void saveMessage(String roomId, String sender, String message) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("해당 채팅방이 존재하지 않습니다."));
        Chat chatMessage = new Chat();
        chatMessage.setChatRoom(chatRoom);
        chatMessage.setSender(sender);
        chatMessage.setMessage(message);
        chatMessage.setSentAt(LocalDateTime.now());
        chatRepository.save(chatMessage);
    }

    public List<Chat> getRecentMessages(String roomId) {
        return chatRepository.findRecentMessages(roomId);
    }
}
