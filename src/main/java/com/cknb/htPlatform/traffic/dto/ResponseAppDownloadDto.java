package com.cknb.htPlatform.traffic.dto;

import com.cknb.htPlatform.traffic.dto.vo.AppDownloadInterface;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseAppDownloadDto {
    AppDownloadInterface dto;
}
