package com.kh.totalapp.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.totalapp.dto.ChatMessageDTO;
import com.kh.totalapp.dto.ChatRoomResDTO;
import com.kh.totalapp.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Slf4j
@Component
public class WebSocketHandler extends TextWebSocketHandler{
    private final ObjectMapper objectMapper;
    private final ChatService chatService;
    private final Map<WebSocketSession, String> sessionRoomIdMap = new ConcurrentHashMap<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.warn("{}", payload);
        ChatMessageDTO chatMessage = objectMapper.readValue(payload, ChatMessageDTO.class);
        ChatRoomResDTO chatRoom = chatService.findRoomById(chatMessage.getRoomId());

        // 세션과 채팅방 ID를 매핑
        sessionRoomIdMap.put(session, chatMessage.getRoomId());
        chatRoom.handlerActions(session, chatMessage, chatService);
    }
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 세션과 매핑된 채팅방 ID 가져오기
        String roomId = sessionRoomIdMap.remove(session);
        ChatRoomResDTO chatRoom = chatService.findRoomById(roomId);
        if(chatRoom != null) {
            chatRoom.handleSessionClosed(session, chatService);
        } else {
            // chatRoom이 null인 경우에 대한 처리
            log.warn("Chat room not found for ID: {}", roomId);
        }

    }
}
