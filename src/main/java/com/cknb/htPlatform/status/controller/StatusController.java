package com.cknb.htPlatform.status.controller;

import com.cknb.htPlatform.status.dto.ResponseChinaMarketStatusDto;
import com.cknb.htPlatform.status.service.ChinaMarketStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "StatusController", description = "상태 관련 API")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RequestMapping("api/status")
public class StatusController {

    private final ChinaMarketStatusService chinaMarketStatusService;

    @GetMapping("chinaMarketRegistration")
    @Operation(summary = "앱별 중국 마켓 등록 현황", description = "앱별 중국 마켓 등록 현황 정보 입니다.")
    public ResponseEntity<ResponseChinaMarketStatusDto> getChinaMarketRegistrationStatus(){
        return ResponseEntity.ok(chinaMarketStatusService.getChinaMarketRegistrationStatus());
    }
}
