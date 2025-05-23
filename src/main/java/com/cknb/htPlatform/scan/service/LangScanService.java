package com.cknb.htPlatform.scan.service;

import com.cknb.htPlatform.scan.dto.ResponseLangScanListDto;
import com.cknb.htPlatform.scan.repository.LangScanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class LangScanService {
    private final LangScanRepository langRepository;

    @Transactional
    public ResponseLangScanListDto getLangScan() throws Exception {
        try{
            return ResponseLangScanListDto.builder()
                    .list(langRepository.getLangScan())
                    .build();
        } catch(Exception e){
            throw new Exception("[언어별 스캔 error]"+ e.getMessage());
        }
    }
}
