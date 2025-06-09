package com.cknb.htPlatform.redis.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Schema(description = "커뮤니티 DB 변동 응답")
public class ResponseCommunityPostDto {
    @JsonProperty("app_kind")
    private String appKind;
    @JsonProperty("type")
    private String type;
    @JsonProperty("title")
    private String title;
    @JsonProperty("user_no")
    private String userNo;
    @JsonProperty("user_nickname")
    private String userNickname;
    @JsonProperty("create_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createDate;
}
