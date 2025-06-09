package com.cknb.htPlatform.traffic.dto;

import com.cknb.htPlatform.traffic.dto.vo.AppChinaScanInterface;
import com.cknb.htPlatform.traffic.dto.vo.AppScanInterface;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseAppScanDto {
    AppScanInterface dto;
    AppChinaScanInterface chinaDto;
}
