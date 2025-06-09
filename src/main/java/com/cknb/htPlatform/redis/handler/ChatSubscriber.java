package com.cknb.htPlatform.redis.handler;

import com.cknb.htPlatform.Enum.RedisChannel;
import com.cknb.htPlatform.redis.dto.ResponseChatDto;
import com.cknb.htPlatform.websocket.handler.WebSocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.util.Map;


@Component
@RequiredArgsConstructor
@Slf4j
public class ChatSubscriber implements MessageListener {
    private final RedisMessageListenerContainer listenerContainer;
    private final WebSocketHandler webSocketHandler;
    private final String TRADE_CHAT = RedisChannel.TRADE_CHAT_MONITOR.getChannelName();
    private final String CHAT = RedisChannel.CHAT_MONITOR.getChannelName();

    @PostConstruct
    public void subscribeToRedis() {
        // 채팅 웹소켓(ht_app_websocket) 채널 구독
        listenerContainer.addMessageListener(this, new ChannelTopic(RedisChannel.TRADE_CHAT_MONITOR.getChannelName()));
        listenerContainer.addMessageListener(this, new ChannelTopic(RedisChannel.CHAT_MONITOR.getChannelName()));
        log.info("✅ Chat Redis 채널 구독 시작");
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String body = new String(message.getBody(), StandardCharsets.UTF_8);
            String channel = new String(message.getChannel(), StandardCharsets.UTF_8);
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> changeEvent = mapper.readValue(body, Map.class);

            if( channel == TRADE_CHAT){
                log.info("[] Redis 메시지 수신 - 중고 거래 채팅: %s, 메시지: %s%n", channel, body);
            }else if(channel == CHAT){
                log.info("[] Redis 메시지 수신 - 일반 채팅: %s, 메시지: %s%n", channel, body);
            }

            String messageType = (String) changeEvent.get("messageType");
            String content = (String) changeEvent.get("message");
            String nickName = (String) changeEvent.get("nickName");
            String timestamp = (String)  changeEvent.get("timestamp");

            log.info("✅ Chat 이벤트 파싱 완료:");

            webSocketHandler.broadcast(channel,  ResponseChatDto.builder()
                    .type(messageType)
                    .content(content)
                    .nickName(nickName)
                    .createDate(timestamp)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
