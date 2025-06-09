package com.cknb.htPlatform.traffic.service;


import com.cknb.htPlatform.traffic.dto.ResponseAppDownloadDto;
import com.cknb.htPlatform.traffic.dto.ResponseAppExeDto;
import com.cknb.htPlatform.traffic.dto.ResponseAppScanDto;
import com.cknb.htPlatform.traffic.dto.ResponseScanInfoDto;
import com.cknb.htPlatform.traffic.repository.AppDownloadRepository;
import com.cknb.htPlatform.traffic.repository.AppExeRepository;
import com.cknb.htPlatform.traffic.repository.AppScanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TrafficService {
    @Value("${aes.key}")
    private String AESKEY;
    private final AppDownloadRepository downloadRepository;
    private final AppExeRepository exeRepository;
    private final AppScanRepository scanRepository;

    public ResponseAppDownloadDto getAppDownload() {
        return ResponseAppDownloadDto.builder()
                        .dto(downloadRepository.getDownload())
                        .build();
    }

    public ResponseAppExeDto getAppExe() {
        return ResponseAppExeDto.builder()
                .dto(exeRepository.getExe())
                .chinaDto(exeRepository.getChinaExe())
                .build();
    }

    public ResponseAppScanDto getAppScan() {
        return ResponseAppScanDto.builder()
                .dto(scanRepository.getAppScan())
                .chinaDto(scanRepository.getChinaScan())
                .build();
    }

    public ResponseScanInfoDto getScanInfo(Integer selectedDays) {
        return ResponseScanInfoDto.builder()
                .dto(scanRepository.getScanInfo(selectedDays))
                .fakeDto(scanRepository.getFakeScanInfo(AESKEY,selectedDays))
                .build();
    }

}
