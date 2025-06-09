package com.cknb.htPlatform.redis.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ResponseChatDto {
    @Schema(description = "채팅 타입")
    @JsonProperty("type")
    private String type;
    @Schema(description = "내용")
    @JsonProperty("content")
    private String content;
    @Schema(description = "닉네임")
    @JsonProperty("nick_name")
    private String nickName;
    @Schema(description = "등록 시간")
    @JsonProperty("create_date")
    private String createDate;
}
