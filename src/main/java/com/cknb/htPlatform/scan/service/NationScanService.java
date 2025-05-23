package com.cknb.htPlatform.scan.service;

import com.cknb.htPlatform.scan.dto.ResponseNationScanListDto;
import com.cknb.htPlatform.scan.dto.vo.CustomerTopByNationInterface;
import com.cknb.htPlatform.scan.dto.vo.NationTopInterface;
import com.cknb.htPlatform.scan.dto.vo.NationScanPerPeriodTopInterface;
import com.cknb.htPlatform.scan.repository.NationScanRepository;
import com.cknb.htPlatform.scan.repository.NationTopPerPeriodRepository;
import com.cknb.htPlatform.scan.repository.NationTopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class NationScanService {
    @Value("${aes.key}")
    private String AESKEY;
    private final NationScanRepository nationRepository;
    private final NationTopRepository nationTopRepository;
    private final NationTopPerPeriodRepository nationTopPerPeriodRepository;

    @Transactional
    public ResponseNationScanListDto getNationScan() throws Exception{

        try{
            return ResponseNationScanListDto.builder()
                    .list(nationRepository.getNationScan())
                    .build();
        }catch (Exception e){
            throw new Exception("[국가별 스캔 error]"+ e.getMessage());
        }
    }

    public List<NationTopInterface> getNationTop(Integer limit) {
        return nationTopRepository.getNationTop(limit);
    }


    public List<CustomerTopByNationInterface> getCustomerTopByNation(String addressN, String startDate, String endDate, Integer limit){

        return nationTopRepository.getCustomerTopByNation(AESKEY, addressN.trim(), startDate, endDate, limit);
    }

    public List<NationScanPerPeriodTopInterface> getYearlyNationScanTop() {
        return nationTopPerPeriodRepository.getYearlyNationScanTop();
    }
    public List<NationScanPerPeriodTopInterface> getMonthlyNationTop() {
        return nationTopPerPeriodRepository.getMonthlyNationTop();
    }
    public List<NationScanPerPeriodTopInterface> getWeeklyNationTop() {
        return nationTopPerPeriodRepository.getWeeklyNationTop();
    }

}
