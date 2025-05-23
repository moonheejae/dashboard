package com.cknb.htPlatform.scan.service;

import com.cknb.htPlatform.scan.dto.ResponseDeviceScanListDto;
import com.cknb.htPlatform.scan.repository.DeviceScanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class DeviceScanService {

    private final DeviceScanRepository deviceRepository;

    @Transactional
    public ResponseDeviceScanListDto getDeviceScan() throws Exception {
        try {
            return ResponseDeviceScanListDto.builder()
                    .list(deviceRepository.getDeviceScan())
                    .build();
        } catch (Exception e){
            throw new Exception("[디바이스별 스캔 error]"+ e.getMessage());
        }
    }

}
