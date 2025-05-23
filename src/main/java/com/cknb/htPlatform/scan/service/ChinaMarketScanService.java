package com.cknb.htPlatform.scan.service;

import com.cknb.htPlatform.scan.dto.ResponseChinaMarketScanListDto;
import com.cknb.htPlatform.scan.repository.ChinaMarketScanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ChinaMarketScanService {
    private final ChinaMarketScanRepository chinaMarketScanRepository;

    @Transactional
    public ResponseChinaMarketScanListDto getChinaMarketScan() throws Exception {
        try {
            return ResponseChinaMarketScanListDto.builder()
                    .list(chinaMarketScanRepository.getChinaMarketScan())
                    .build();
        }catch (Exception e){
            throw new Exception("[중국 마켓별 스캔 error]"+ e.getMessage());
        }
    }
}
