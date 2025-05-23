package com.cknb.htPlatform.Enum;

import lombok.Getter;

@Getter
public enum CommentCategory {
    TOTAL(1),
    PLAY_STORE(2),
    APP_STORE(3),
    CHINA_MARKET(4),
    DOWNLOAD_TOTAL(5),
    DEVICE_EXE(6),
    DEVICE_SCAN(7),
    CHINA_MARKET_SCAN(8),
    CHINA_MARKET_EXE(9)/*,
    DEVICE_SCAN(7, "devicescan"),
    DEVICE_EXE(6, "deviceexe"),
    DEVICE_SCAN(7, "devicescan"),
    DEVICE_EXE(6, "deviceexe"),
    DEVICE_SCAN(7, "devicescan")*/;

    private final int code;

    CommentCategory(int code) {
        this.code = code;
    }

    public CommentCategory getCode(int code) {
        for (CommentCategory category : CommentCategory.values()) {
            if (category.getCode() == code) {
                return category;
            }
        }
        throw new IllegalArgumentException("Invalid Category code: " + code);
    }
}