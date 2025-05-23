package com.cknb.htPlatform.scan.dto;

import com.cknb.htPlatform.scan.dto.vo.MarketScanInterface;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMarketScanListDto {
    List<MarketScanInterface> list;
}
