package com.cknb.htPlatform.traffic.dto;

import com.cknb.htPlatform.traffic.dto.vo.AppChinaExeInterface;
import com.cknb.htPlatform.traffic.dto.vo.AppExeInterface;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseAppExeDto {
    AppExeInterface dto;
    AppChinaExeInterface chinaDto;
}
