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
@Schema(description = "실행 DB 변동 응답")
public class ResponseExeDto {
    @JsonProperty("app_kind")
    private String appKind;
    @JsonProperty("address_n")
    private String addressN;
    @JsonProperty("device_imei")
    private String deviceImei;
    @JsonProperty("create_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createDate;
}
