package com.cknb.htPlatform.scan.controller;

import com.cknb.htPlatform.dto.BaseResponseDto;
import com.cknb.htPlatform.scan.dto.*;
import com.cknb.htPlatform.scan.dto.vo.*;
import com.cknb.htPlatform.scan.service.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RequestMapping("api/scan")
public class ScanController {

    private final DeviceScanService deviceService;
    private final MarketScanService marketService;
    private final OsScanService osService;
    private final LangScanService langService;
    private final ChinaMarketScanService chinaMarketScanService;
    private final CustomerScanService customerService;
    private final NationScanService nationService;
    private final CategoryScanService categoryService;
    private final AppService appService;
    private final MonthlyScanService monthlyScanService;

    @GetMapping("device")
    public ResponseEntity<BaseResponseDto<ResponseDeviceScanListDto>> getDeviceScan() {
        try{
            ResponseDeviceScanListDto list = deviceService.getDeviceScan();
            return ResponseEntity.ok(
                    BaseResponseDto.<ResponseDeviceScanListDto>builder()
                            .statusCode(200)
                            .status("성공")
                            .data(list)
                            .build()
            );
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BaseResponseDto.<ResponseDeviceScanListDto>builder()
                            .statusCode(500)
                            .status("디바이스별 Scan 조회 에러 발생!")
                            .build()
                    );
        }
    }

    @GetMapping("market")
    public ResponseEntity<BaseResponseDto<ResponseMarketScanListDto>> getMarketScan() {
        try{
            ResponseMarketScanListDto list = marketService.getMarketScan();
            return ResponseEntity.ok(
                    BaseResponseDto.<ResponseMarketScanListDto>builder()
                            .statusCode(200)
                            .status("성공")
                            .data(list)
                            .build()
            );
        } catch (Exception e){
            return ResponseEntity.ok(
                    BaseResponseDto.<ResponseMarketScanListDto>builder()
                            .statusCode(500)
                            .status("마켓별 Scan 조회 에러 발생!")
                            .build());
        }
    }

    @GetMapping("os")
    public ResponseEntity<BaseResponseDto<ResponseOsScanListDto>> getOsScan() {
        try{
            ResponseOsScanListDto list = osService.getOsScan();
            return ResponseEntity.ok(
                    BaseResponseDto.<ResponseOsScanListDto>builder()
                            .statusCode(200)
                            .status("성공")
                            .data(list)
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.ok(
                    BaseResponseDto.<ResponseOsScanListDto>builder()
                            .statusCode(500)
                            .status("OS별 Scan 조회 에러 발생!")
                            .build());
        }
    }

    @GetMapping("lang")
    public ResponseEntity<BaseResponseDto<ResponseLangScanListDto>> getLangScan() {
        try{
            ResponseLangScanListDto list = langService.getLangScan();
            return ResponseEntity.ok(
                    BaseResponseDto.<ResponseLangScanListDto>builder()
                            .statusCode(200)
                            .status("성공")
                            .data(list)
                            .build()
            );
        }catch (Exception e){
            return ResponseEntity.ok(
                    BaseResponseDto.<ResponseLangScanListDto>builder()
                            .statusCode(500)
                            .status("언어별 Scan 조회 에러 발생!")
                            .build());
        }
    }

    @GetMapping("nation")
    public ResponseEntity<BaseResponseDto<ResponseNationScanListDto>> getNationScan() {
        try{
            ResponseNationScanListDto list = nationService.getNationScan();
            return ResponseEntity.ok(
                    BaseResponseDto.<ResponseNationScanListDto>builder()
                            .statusCode(200)
                            .status("성공")
                            .data(list)
                            .build()
            );
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BaseResponseDto.<ResponseNationScanListDto>builder()
                            .statusCode(500)
                            .status("국가별 Scan 조회 에러 발생!")
                            .build()
                    );
        }
    }

    @GetMapping("chinaMarket")
    public ResponseEntity<BaseResponseDto<ResponseChinaMarketScanListDto>> getChinaMarketScan() {
        try{
            ResponseChinaMarketScanListDto list = chinaMarketScanService.getChinaMarketScan();
            return ResponseEntity.ok(
                    BaseResponseDto.<ResponseChinaMarketScanListDto>builder()
                            .statusCode(200)
                            .status("성공")
                            .data(list)
                            .build()
            );
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BaseResponseDto.<ResponseChinaMarketScanListDto>builder()
                            .statusCode(500)
                            .status("중국 마켓별 Scan 조회 에러 발생!")
                            .build()
                    );
        }
    }

    @Operation(summary = "상위 고객사 스캔 현황", description = "월별로 스캔 상위 고객사의 월별 스캔수를 가져옵니다.")
    @GetMapping("customerScan")
    public ResponseEntity<BaseResponseDto<List<ResponseCustomerScanTopDto>>> getCustomerScan(
            @RequestParam("start_month")String startDate,
            @RequestParam("end_month")String endDate
    ) {
        try {
            return ResponseEntity.ok(
                    BaseResponseDto.<List<ResponseCustomerScanTopDto>>builder()
                            .statusCode(200)
                            .status("성공")
                            .data(customerService.getCustomerScanTop(startDate, endDate))
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BaseResponseDto.<List<ResponseCustomerScanTopDto>>builder()
                            .statusCode(500)
                            .status("고객사별 Top Scan 조회 에러 발생!")
                            .build()
                    );
        }
    }

    @Operation(summary = "고객사 TOP 100 (총 스캔수, 총 발주량)", description = "월별 테이블에 적재되어있는 년도(현재 2019)부터 누적 스캔, 발주량을 가져옵니다.")
    @GetMapping("customerScanTotal")
    public ResponseEntity<BaseResponseDto<List<ResponseCustomerScanTopDto>>> getCustomerScanTotal(
            @RequestParam("start_month")String startDate,
            @RequestParam("end_month")String endDate
    ) {
        try {
            return ResponseEntity.ok(
                    BaseResponseDto.<List<ResponseCustomerScanTopDto>>builder()
                            .statusCode(200)
                            .status("성공")
                            .data(customerService.getCustomerScanTotal(startDate, endDate))
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BaseResponseDto.<List<ResponseCustomerScanTopDto>>builder()
                            .statusCode(500)
                            .status("고객사별 Top Scan 조회 에러 발생!"+e.getMessage())
                            .build()
                    );
        }
    }

    @Operation(summary = "국가별 스캔 TOP")
    @GetMapping("nationTop")
    public ResponseEntity<BaseResponseDto<List<NationTopInterface>>> getNationTop(
            @RequestParam("limit")Integer limit
    ) {
        try {
            return ResponseEntity.ok(
                    BaseResponseDto.<List<NationTopInterface>>builder()
                            .statusCode(200)
                            .status("성공")
                            .data(nationService.getNationTop(limit))
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BaseResponseDto.<List<NationTopInterface>>builder()
                            .statusCode(500)
                            .status("국가별 Top Scan 조회 에러 발생!")
                            .build()
                    );
        }
    }

    @Operation(summary = "국가별 TOP 스캔 업체")
    @GetMapping("customerTopByNation")
    public ResponseEntity<BaseResponseDto<List<CustomerTopByNationInterface>>> getNationCustomerTop(
            @Nullable @RequestParam("address_n")String addressN,
            @Nullable @RequestParam("start_date")String startDate,
            @Nullable @RequestParam("end_date")String endDate,
            @RequestParam("limit")Integer limit
    ) {
        try {
            return ResponseEntity.ok(
                    BaseResponseDto.<List<CustomerTopByNationInterface>>builder()
                            .statusCode(200)
                            .status("성공")
                            .data(nationService.getCustomerTopByNation(addressN, startDate, endDate, limit))
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BaseResponseDto.<List<CustomerTopByNationInterface>>builder()
                            .statusCode(500)
                            .status("선택된 국가의 업체별 Top Scan 조회 에러 발생!")
                            .build()
                    );
        }
    }

    @Operation(summary = "국가별 스캔 TOP 10 (최근 1년)")
    @GetMapping("yearlyNationTop")
    public ResponseEntity<BaseResponseDto<List<NationScanPerPeriodTopInterface>>> getYearlyNationTop(){
        try {
            return ResponseEntity.ok(
                    BaseResponseDto.<List<NationScanPerPeriodTopInterface>>builder()
                            .statusCode(200)
                            .status("성공")
                            .data(nationService.getYearlyNationScanTop())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BaseResponseDto.<List<NationScanPerPeriodTopInterface>>builder()
                            .statusCode(500)
                            .status("연도별 국가 Top Scan 조회 에러 발생!"+ e.getMessage())
                            .build()
                    );
        }
    }

    @Operation(summary = "국가별 스캔 TOP 10 (최근 1개월)")
    @GetMapping("monthlyNationTop")
    public ResponseEntity<BaseResponseDto<List<NationScanPerPeriodTopInterface>>> getMonthlyNationTop(){
        try {
            return ResponseEntity.ok(
                    BaseResponseDto.<List<NationScanPerPeriodTopInterface>>builder()
                            .statusCode(200)
                            .status("성공")
                            .data(nationService.getMonthlyNationTop())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BaseResponseDto.<List<NationScanPerPeriodTopInterface>>builder()
                            .statusCode(500)
                            .status("월별 국가 Top Scan 조회 에러 발생!"+ e.getMessage())
                            .build()
                    );
        }
    }

    @Operation(summary = "국가별 스캔 TOP 10 (최근 1주)")
    @GetMapping("weeklyNationTop")
    public ResponseEntity<BaseResponseDto<List<NationScanPerPeriodTopInterface>>> getWeeklyNationTop(){
        try {
            return ResponseEntity.ok(
                    BaseResponseDto.<List<NationScanPerPeriodTopInterface>>builder()
                            .statusCode(200)
                            .status("성공")
                            .data(nationService.getWeeklyNationTop())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BaseResponseDto.<List<NationScanPerPeriodTopInterface>>builder()
                            .statusCode(500)
                            .status("최근1주 국가 Top Scan 조회 에러 발생!"+ e.getMessage())
                            .build()
                    );
        }
    }

    @Operation(summary = "업체별 스캔 TOP")
    @GetMapping("customerTop")
    public ResponseEntity<BaseResponseDto<List<CustomerTopByNationInterface>>> getNationCustomerTop(
            @RequestParam("limit") Integer limit
    ) {
        try {
            return ResponseEntity.ok(
                    BaseResponseDto.<List<CustomerTopByNationInterface>>builder()
                            .statusCode(200)
                            .status("성공")
                            .data(customerService.getCustomerTop(limit))
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BaseResponseDto.<List<CustomerTopByNationInterface>>builder()
                            .statusCode(500)
                            .status("선택된 국가의 업체별 Top Scan 조회 에러 발생!")
                            .build()
                    );
        }
    }


    @Operation(summary = "업체별 국가 TOP")
      @GetMapping("nationTopByCustomer")
      public ResponseEntity<BaseResponseDto<List<NationTopByCustomerInterface>>> getNationTopByCustomer(
            @RequestParam("customerName")String customerName
    ){
          try {
              return ResponseEntity.ok(
                      BaseResponseDto.<List<NationTopByCustomerInterface>>builder()
                              .statusCode(200)
                              .status("성공")
                              .data(customerService.getNationTopByCustomer(customerName))
                              .build()
              );
          } catch (Exception e) {
              return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                      .body(BaseResponseDto.<List<NationTopByCustomerInterface>>builder()
                              .statusCode(500)
                              .status("업체별 국가 Top Scan 조회 에러 발생!"+ e.getMessage())
                              .build()
                      );
          }
      }


    @Operation(summary = "업체별 스캔 TOP 10 (최근 1년)")
    @GetMapping("yearlyCustomerTop")
    public ResponseEntity<BaseResponseDto<List<CustomerScanPerPeriodTopInterface>>> getYearlyCustomerTop(){
        try {
            return ResponseEntity.ok(
                    BaseResponseDto.<List<CustomerScanPerPeriodTopInterface>>builder()
                            .statusCode(200)
                            .status("성공")
                            .data(customerService.getYearlyCustomerScanTop())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BaseResponseDto.<List<CustomerScanPerPeriodTopInterface>>builder()
                            .statusCode(500)
                            .status("연도별 업체 Top Scan 조회 에러 발생!"+ e.getMessage())
                            .build()
                    );
        }
    }

    @Operation(summary = "업체별 스캔 TOP 10 (최근 1개월)")
    @GetMapping("monthlyCustomerTop")
    public ResponseEntity<BaseResponseDto<List<CustomerScanPerPeriodTopInterface>>> getMonthlyCustomerTop(){
        try {
            return ResponseEntity.ok(
                    BaseResponseDto.<List<CustomerScanPerPeriodTopInterface>>builder()
                            .statusCode(200)
                            .status("성공")
                            .data(customerService.getMonthlyCustomerTop())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BaseResponseDto.<List<CustomerScanPerPeriodTopInterface>>builder()
                            .statusCode(500)
                            .status("월별 업체 Top Scan 조회 에러 발생!"+ e.getMessage())
                            .build()
                    );
        }
    }

    @Operation(summary = "업체별 스캔 TOP 10 (최근 1주)")
    @GetMapping("weeklyCustomerTop")
    public ResponseEntity<BaseResponseDto<List<CustomerScanPerPeriodTopInterface>>> getWeeklyCustomerTop(){
        try {
            return ResponseEntity.ok(
                    BaseResponseDto.<List<CustomerScanPerPeriodTopInterface>>builder()
                            .statusCode(200)
                            .status("성공")
                            .data(customerService.getWeeklyCustomerTop())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BaseResponseDto.<List<CustomerScanPerPeriodTopInterface>>builder()
                            .statusCode(500)
                            .status("최근1주 업체 Top Scan 조회 에러 발생!"+ e.getMessage())
                            .build()
                    );
        }
    }

    @GetMapping("category")
    public ResponseEntity<BaseResponseDto<ResponseCategoryScanListDto>> getCategoryScan(
            @RequestParam("start_month")String startMonth,
            @RequestParam("end_month")String endMonth
    ) {
        try{
            return ResponseEntity.ok(
                    BaseResponseDto.<ResponseCategoryScanListDto>builder()
                            .statusCode(200)
                            .status("성공")
                            .data(categoryService.getCategoryScan(startMonth, endMonth))
                            .build()
            );
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BaseResponseDto.<ResponseCategoryScanListDto>builder()
                            .statusCode(500)
                            .status("카테고리별 Scan 조회 에러 발생!")
                            .build()
                    );
        }
    }

    @GetMapping("app")
    public ResponseEntity<BaseResponseDto<ResponseAppScanListDto>> getAppScan(
            @RequestParam("start_month")String startMonth,
            @RequestParam("end_month")String endMonth
    ) {
        try{
            return ResponseEntity.ok(
                    BaseResponseDto.<ResponseAppScanListDto>builder()
                            .statusCode(200)
                            .status("성공")
                            .data(appService.getAppScan(startMonth, endMonth))
                            .build()
            );
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BaseResponseDto.<ResponseAppScanListDto>builder()
                            .statusCode(500)
                            .status("카테고리별 Scan 조회 에러 발생!")
                            .build()
                    );
        }
    }

    @GetMapping("monthlyScan")
    public ResponseEntity<BaseResponseDto<ResponseMonthlyScanListDto>> getMonthlyScan(
            @RequestParam("year")String year,
            @RequestParam("month")String month
    ) {
        try{
            return ResponseEntity.ok(
                    BaseResponseDto.<ResponseMonthlyScanListDto>builder()
                            .statusCode(200)
                            .status("성공")
                            .data(monthlyScanService.getMonthlyScan(year, month))
                            .build()
            );
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BaseResponseDto.<ResponseMonthlyScanListDto>builder()
                            .statusCode(500)
                            .status("카테고리별 Scan 조회 에러 발생!")
                            .build()
                    );
        }
    }
}
