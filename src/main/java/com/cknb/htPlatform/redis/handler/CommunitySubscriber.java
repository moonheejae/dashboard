package com.cknb.htPlatform.redis.handler;

import com.cknb.htPlatform.Enum.RedisChannel;
import com.cknb.htPlatform.redis.dto.ResponseCommunityPostDto;
import com.cknb.htPlatform.websocket.handler.WebSocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class CommunitySubscriber implements MessageListener {
    private final RedisMessageListenerContainer listenerContainer;
    private final WebSocketHandler webSocketHandler;

    @PostConstruct
    public void subscribeToRedis() {
        listenerContainer.addMessageListener(this, new ChannelTopic(RedisChannel.COMMUNITY_MONITOR.getChannelName()));
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String body = new String(message.getBody(), StandardCharsets.UTF_8);
            String channel = new String(message.getChannel(), StandardCharsets.UTF_8);

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> changeEvent = mapper.readValue(body, Map.class);

            String appKind = (String) changeEvent.get("appKind"); // HT, COP, Global
            String tableName = (String) changeEvent.get("tableName"); // Trade, CommDebate, CommReview, CommTip
            String title = (String) changeEvent.get("title");
            String userNo = changeEvent.get("userNo").toString()
                    .replaceAll(".*,\\s*", "")
                    .replaceAll("[\\]\\s]", "");
            String userNickname = (String) changeEvent.get("userNickname");
            String createDate = (String) changeEvent.get("createDate");

            Object entity = changeEvent.get("entity");

            log.info("[] Redis 메시지 수신 - 커뮤니티 : %s, 내용: %s%n", channel, body);
            webSocketHandler.broadcast(channel,ResponseCommunityPostDto.builder()
                    .appKind(appKind)
                    .type(tableName)
                    .userNo(userNo)
                    .userNickname(userNickname)
                    .title(title)
                    .createDate(createDate)
                    .build());

        } catch (Exception e) {
            log.error("community 구독 실패 ", e);
        }
    }

}
