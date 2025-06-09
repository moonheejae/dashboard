package com.cknb.htPlatform.redis.handler;

import com.cknb.htPlatform.Enum.RedisChannel;
import com.cknb.htPlatform.redis.dto.ResponseExeDto;
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
public class ExeSubscriber implements MessageListener {
    private final RedisMessageListenerContainer listenerContainer;
    private final WebSocketHandler webSocketHandler;

    @PostConstruct
    public void subscribeToRedis() {
        listenerContainer.addMessageListener(this, new ChannelTopic(RedisChannel.EXE_MONITOR.getChannelName()));
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String body = new String(message.getBody(), StandardCharsets.UTF_8);
            String channel = new String(message.getChannel(), StandardCharsets.UTF_8);

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> changeEvent = mapper.readValue(body, Map.class);

            String appKind = (String) changeEvent.get("appKind");
            String addressN = (String) changeEvent.get("addressN");
            String exeTime = (String) changeEvent.get("exeTime");
            String deviceImei = (String) changeEvent.get("deviceImei");

            webSocketHandler.broadcast(channel, ResponseExeDto.builder()
                    .appKind(appKind)
                    .addressN(addressN)
                    .deviceImei(deviceImei)
                    .createDate(exeTime)
                    .build());

        } catch (Exception e) {
        }
    }
}
