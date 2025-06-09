package com.cknb.htPlatform.Enum;

import lombok.Getter;

@Getter
public enum RedisChannel {
    // 채팅
    TRADE_CHAT_MONITOR("trade_chat_monitor", "중고거래 채팅"),
    CHAT_MONITOR("chat_monitor", "1:1 채팅"),

    // 커뮤니티
    COMMUNITY_MONITOR("community_monitor", "커뮤니티"),

    // 실행, 스캔
    EXE_MONITOR("exe_monitor", "실행 모니터링"),
    SCAN_MONITOR("scan_monitor", "스캔 모니터링");


    private final String channelName;
    private final String description;

    RedisChannel(String channelName, String description) {
        this.channelName = channelName;
        this.description = description;
    }
}
