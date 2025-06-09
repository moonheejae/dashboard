package com.cknb.htPlatform.status.dto;

import com.cknb.htPlatform.status.dto.vo.ChinaMarketRegistrationRateInterface;
import com.cknb.htPlatform.status.dto.vo.ChinaMarketStatusInterface;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Schema(description = "중국 마켓 등록 현황 응답")
public class ResponseChinaMarketStatusDto {
    List<ChinaMarketStatusInterface> dto;
    List<ChinaMarketRegistrationRateInterface> rateDto;
}
