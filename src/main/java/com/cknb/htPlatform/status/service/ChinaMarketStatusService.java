package com.cknb.htPlatform.status.service;

import com.cknb.htPlatform.status.dto.ResponseChinaMarketStatusDto;
import com.cknb.htPlatform.status.repository.ChinaMarketRegistrationStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ChinaMarketStatusService {

    private final ChinaMarketRegistrationStatusRepository chinaMarketRegistrationStatusRepository;

    public ResponseChinaMarketStatusDto getChinaMarketRegistrationStatus(){
        return ResponseChinaMarketStatusDto.builder()
                .dto(chinaMarketRegistrationStatusRepository.getChinaMarketRegistrationStatus())
                .rateDto(chinaMarketRegistrationStatusRepository.getChinaMarketRegistrationRate())
                .build();
    }
}
