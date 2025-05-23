package com.cknb.htPlatform.scan.service;

import com.cknb.htPlatform.scan.dto.ResponseCategoryScanListDto;
import com.cknb.htPlatform.scan.repository.CategoryScanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CategoryScanService {
    @Value("${aes.key}")
    private String AESKEY;
    private final CategoryScanRepository categoryScanRepository;

    @Transactional
    public ResponseCategoryScanListDto getCategoryScan(String startMonth, String endMonth) throws Exception{
        try{
            return ResponseCategoryScanListDto.builder()
                    .list(categoryScanRepository.getCategoryScan(AESKEY, startMonth, endMonth))
                    .build();
        }catch (Exception e){
            throw new Exception("[카테고리별 스캔 error]"+ e.getMessage());
        }
    }
}
