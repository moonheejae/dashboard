package com.cknb.htPlatform.traffic.controller;

import com.cknb.htPlatform.traffic.dto.*;
import com.cknb.htPlatform.traffic.service.CommunityService;
import com.cknb.htPlatform.traffic.service.ExeByMarketAppService;
import com.cknb.htPlatform.traffic.service.TrafficService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "TrafficController", description = "앱 실행, 스캔, 다운로드 트래픽 관련 API")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RequestMapping("api/traffic")
public class TrafficController {

    private final TrafficService trafficService;
    private final ExeByMarketAppService exeByMarketAppService;
    private final CommunityService communityService;

    @GetMapping("download")
    @Operation(summary = "각 마켓별 다운로드 및 총합", description = "IOS, Android, 중국마켓의 다운로드 총합 입니다.")
    public ResponseEntity<ResponseAppDownloadDto> getAppDownload(){
        return ResponseEntity.ok(trafficService.getAppDownload());
    }

    @GetMapping("exe")
    @Operation(summary = "각 마켓별 실행 및 총합", description = "IOS, Android, 중국마켓의 실행 총합 입니다.")
    public ResponseEntity<ResponseAppExeDto> getAppExe(){
        return ResponseEntity.ok(trafficService.getAppExe());
    }

    @GetMapping("scan")
    @Operation(summary = "각 마켓별 스캔 및 총합", description = "IOS, Android, 중국마켓의 스캔 총합 입니다.")
    public ResponseEntity<ResponseAppScanDto> getAppScan(){
        return ResponseEntity.ok(trafficService.getAppScan());
    }

    @GetMapping("scanInfo")
    @Operation(summary = "스캔 및 가품수, 비율", description = "기간별(1, 7, 30일) 전체 스캔수, 가품수, 스캔 대비 가품 비율 입니다.")
    public ResponseEntity<ResponseScanInfoDto> getScanInfo(
            @RequestParam(value = "selected_days")  Integer selectedDays
    ){
        return ResponseEntity.ok(trafficService.getScanInfo(selectedDays));
    }

    @GetMapping("exeByMarketApp")
    @Operation(summary = "마켓별 앱별 실행 점유율", description = "기간별(1, 7, 30일) 마켓별 앱별 실행 점유율 정보 입니다.")
    public ResponseEntity<ResponseExeByMarketAppDto> getExeByMarketApp(
            @RequestParam(value = "selected_days")  Integer selectedDays
    ){
        return ResponseEntity.ok(exeByMarketAppService.getExeByMarketApp(selectedDays));
    }

    @GetMapping("commReviewCount")
    @Operation(summary = "정품 제품 리뷰 - 게시물, 조회, 좋아요, 댓글, 북마크 수", description = "기간별(1, 7, 30일) 정품 제품 리뷰 - 게시물, 조회, 좋아요, 댓글, 북마크 수 정보 입니다.")
    public ResponseEntity<ResponseCommunityDto> getCommReviewCount(
            @RequestParam(value = "selected_days", required = false)  Integer selectedDays
    ){
        // selectedDays 가 있는 경우 해당 기간의 누적 값, 파라미터 없거나 null 인 경우는 전체 누적 값
        return ResponseEntity.ok(communityService.getCommReviewCount(selectedDays));
    }

    @GetMapping("commDebateCount")
    @Operation(summary = "정품 Qna - 게시물, 조회, 좋아요, 댓글, 북마크 수", description = "기간별(1, 7, 30일) 정품 Qna - 게시물, 조회, 좋아요, 댓글, 북마크 수 정보 입니다.")
    public ResponseEntity<ResponseCommunityDto> getCommDebateCount(
            @RequestParam(value = "selected_days", required = false)  Integer selectedDays
    ){
        // selectedDays 가 있는 경우 해당 기간의 누적 값, 파라미터 없거나 null 인 경우는 전체 누적 값
        return ResponseEntity.ok(communityService.getCommDebateCount(selectedDays));
    }

    @GetMapping("commInfoCount")
    @Operation(summary = "정품 판별 tip - 게시물, 조회, 좋아요, 댓글, 북마크 수", description = "기간별(1, 7, 30일) 정품 판별 tip - 게시물, 조회, 좋아요, 댓글, 북마크 수 정보 입니다.")
    public ResponseEntity<ResponseCommunityDto> getCommInfoCount(
            @RequestParam(value = "selected_days", required = false)  Integer selectedDays
    ){
        // selectedDays 가 있는 경우 해당 기간의 누적 값, 파라미터 없거나 null 인 경우는 전체 누적 값
        return ResponseEntity.ok(communityService.getCommInfoCount(selectedDays));
    }
}
