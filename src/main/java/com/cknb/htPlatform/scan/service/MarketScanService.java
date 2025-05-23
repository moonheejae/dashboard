package com.cknb.htPlatform.scan.service;

import com.cknb.htPlatform.scan.dto.ResponseMarketScanListDto;
import com.cknb.htPlatform.scan.repository.MarketScanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class MarketScanService {

    private final MarketScanRepository marketRepository;

    @Transactional
    public ResponseMarketScanListDto getMarketScan() throws Exception {
        try{
            return ResponseMarketScanListDto.builder()
                    .list(marketRepository.getMarketScan())
                    .build();
        } catch(Exception e){
            throw new Exception("[마켓별 스캔 error]"+ e.getMessage());
        }
    }
}
