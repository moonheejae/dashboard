package com.cknb.htPlatform.scan.dto;

import com.cknb.htPlatform.scan.dto.vo.AppScanInterface;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseAppScanListDto {
    List<AppScanInterface> list;
}
