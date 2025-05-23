package com.cknb.htPlatform.scan.service;

import com.cknb.htPlatform.scan.dto.ResponseMonthlyScanListDto;
import com.cknb.htPlatform.scan.repository.MonthlyScanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class MonthlyScanService {

    @Value("${aes.key}")
    private String AESKEY;
    private final MonthlyScanRepository monthlyScanRepository;

    @Transactional
    public ResponseMonthlyScanListDto getMonthlyScan(String year, String month) throws Exception {
        try {
            return ResponseMonthlyScanListDto.builder()
                    .list(monthlyScanRepository.getMonthlyScan(AESKEY,year,month))
                    .build();
        } catch(Exception e){
            throw new Exception("[os별 스캔 error]"+ e.getMessage());
        }
    }
}
