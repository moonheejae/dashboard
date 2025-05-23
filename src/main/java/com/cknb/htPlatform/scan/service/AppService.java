package com.cknb.htPlatform.scan.service;

import com.cknb.htPlatform.scan.dto.ResponseAppScanListDto;
import com.cknb.htPlatform.scan.repository.AppScanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AppService {

    private final AppScanRepository appScanRepository;

    @Transactional
    public ResponseAppScanListDto getAppScan(String startMonth, String endMonth) throws Exception{

        try{
            return ResponseAppScanListDto.builder()
                    .list(appScanRepository.getAppScan(startMonth, endMonth))
                    .build();
        }catch (Exception e){
            throw new Exception("[앱별 스캔 error]"+ e.getMessage());
        }
    }

}
