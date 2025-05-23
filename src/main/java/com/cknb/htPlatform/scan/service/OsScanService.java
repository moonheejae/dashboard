package com.cknb.htPlatform.scan.service;

import com.cknb.htPlatform.scan.dto.ResponseOsScanListDto;
import com.cknb.htPlatform.scan.repository.OsScanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class OsScanService {
    private final OsScanRepository osRepository;

    @Transactional
    public ResponseOsScanListDto getOsScan() throws Exception {
        try {
            return ResponseOsScanListDto.builder()
                    .list(osRepository.getOsScan())
                    .build();
        } catch(Exception e){
            throw new Exception("[os별 스캔 error]"+ e.getMessage());
        }
    }
}
