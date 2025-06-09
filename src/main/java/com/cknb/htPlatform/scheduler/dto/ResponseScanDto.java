package com.cknb.htPlatform.scheduler.dto;

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
@Schema(description = "스캔 DB 스케줄러 응답")
public class ResponseScanDto {
    @JsonProperty("idx")
    private String idx;
    @JsonProperty("det_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String detTime;
    @JsonProperty("address_n")
    private String addressN;
    @JsonProperty("device_imei")
    private String deviceImei;
    @JsonProperty("iqr_condition")
    private String iqrCondition;
    @JsonProperty("customer_name")
    private String customerName;
    @JsonProperty("brand_name")
    private String brandName;
    @JsonProperty("app_gubun")
    private String appGubun;
}
