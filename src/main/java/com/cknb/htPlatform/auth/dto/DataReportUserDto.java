package com.cknb.htPlatform.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataReportUserDto {
    @JsonProperty("no")
    private Long no;
    @JsonProperty("user_no")
    private String userNo;
    @JsonProperty("session_id")
    private String sessionId;
    @JsonProperty("expiry")
    private Date expiry;
    @JsonProperty("token")
    private String token;
}
