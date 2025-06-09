package com.cknb.htPlatform.traffic.dto;

import com.cknb.htPlatform.traffic.dto.vo.FakeScanInfoInterface;
import com.cknb.htPlatform.traffic.dto.vo.ScanInfoInterface;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseScanInfoDto {
    ScanInfoInterface dto;
    List<FakeScanInfoInterface> fakeDto;
}
