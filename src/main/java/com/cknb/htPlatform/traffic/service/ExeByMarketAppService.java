package com.cknb.htPlatform.traffic.service;

import com.cknb.htPlatform.traffic.dto.ResponseExeByMarketAppDto;
import com.cknb.htPlatform.traffic.dto.ResponseScanInfoDto;
import com.cknb.htPlatform.traffic.dto.vo.ExeByMarketAppInterface;
import com.cknb.htPlatform.traffic.repository.ExeByMarketAppRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ExeByMarketAppService {

    private final ExeByMarketAppRepository exeByMarketAppRepository;

    public ResponseExeByMarketAppDto getExeByMarketApp(Integer selectedDays) {
        return ResponseExeByMarketAppDto.builder()
                .dto(exeByMarketAppRepository.getExeByMarketApp(selectedDays))
                .build();
    }
}
