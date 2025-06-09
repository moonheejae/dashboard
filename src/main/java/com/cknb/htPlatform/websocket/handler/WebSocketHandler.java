package com.cknb.htPlatform.websocket.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {

    private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        log.info("✅ 연결: {} (총 {}개)", session.getId(), sessions.size());

        // 환영 메시지 전송
        sendMessage(session, "welcome", "연결되었습니다!");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
        log.info("❌ 연결 종료: {}", session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        log.info("📨 메시지 수신: {}", message.getPayload());

        // 에코 응답
        sendMessage(session, "echo", "받은 메시지: " + message.getPayload());
    }

    // 모든 클라이언트에게 브로드캐스트
    public void broadcast(String type, Object data) {
        Map<String, Object> message = Map.of(
                "type", type,
                "data", data,
                "timestamp", System.currentTimeMillis()
        );

        String json = toJson(message);
        sessions.removeIf(session -> !send(session, json));

        log.info("📡 브로드캐스트: {} → {}개", type, sessions.size());
    }

    // 개별 메시지 전송
    private void sendMessage(WebSocketSession session, String type, Object data) {
        Map<String, Object> message = Map.of("type", type, "data", data);
        send(session, toJson(message));
    }

    // 안전한 전송
    private boolean send(WebSocketSession session, String message) {
        try {
            if (session.isOpen()) {
                session.sendMessage(new TextMessage(message));
                return true;
            }
        } catch (Exception e) {
            log.debug("전송 실패: {}", session.getId());
        }
        return false;
    }

    private String toJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.error("JSON 변환 실패!",e);
            return "{\"error\":\"JSON 변환 실패\"}";
        }
    }
}
