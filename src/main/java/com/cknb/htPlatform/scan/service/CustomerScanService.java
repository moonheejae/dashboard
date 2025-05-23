package com.cknb.htPlatform.scan.service;

import com.cknb.htPlatform.scan.dto.ResponseCustomerScanTopDto;
import com.cknb.htPlatform.scan.dto.vo.*;
import com.cknb.htPlatform.scan.repository.CustomerRepository;
import com.cknb.htPlatform.scan.repository.CustomerTopPerPeriodRepository;
import com.cknb.htPlatform.scan.repository.CustomerTopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CustomerScanService {

    @Value("${aes.key}")
    private String AESKEY;
    private final CustomerRepository customerRepository;
    private final CustomerTopRepository customerTopRepository;
    private final CustomerTopPerPeriodRepository customerTopPerPeriodRepository;


    // 고객사 기준 - 월별 고객사 스캔 현황
    public  List<ResponseCustomerScanTopDto> getCustomerScanTop(String startDate, String endDate){
        List<CustomerScanTopInterface> customerScanTop =  customerRepository.getCustomerScanTop(AESKEY, startDate, endDate);

        List<ResponseCustomerScanTopDto> list = customerScanTop.stream()
                .map( scan -> ResponseCustomerScanTopDto.builder()
                                        .monthDate(scan.getMonthDate())
                                        .title(scan.getTitle())
                                        .brandName(scan.getBrandName())
                                        .scanCount(scan.getScanCount())
                                        .build())
                .collect(Collectors.toList());

        return list;
    }

    public  List<ResponseCustomerScanTopDto> getCustomerScanTotal(String startDate, String endDate){
        List<CustomerScanTopInterface> customerScanTop = customerRepository.getCustomerScanTop(AESKEY, startDate, endDate);
        // 고객사별 총 스캔 횟수 (title 기준)
        Map<String, Integer> totalScanMap = customerScanTop.stream()
                .collect(Collectors.groupingBy(
                        CustomerScanTopInterface::getTitle,
                        Collectors.summingInt(CustomerScanTopInterface::getScanCount)
                ));

        List<CustomerOrderTopInterface> customerOrderTop = customerRepository.getCustomerOrderTop(AESKEY, startDate, endDate);
        // 고객사별 총 발주량 (title 기준)
        Map<String, CustomerOrderTopInterface> orderMap = customerOrderTop.stream()
                .collect(Collectors.toMap(
                        CustomerOrderTopInterface::getTitle,
                        item -> item,
                        // 중복 키가 있을 경우 더 큰 orderCount를 가진 항목 선택
                        (existing, replacement) ->
                                existing.getOrderCount() >= replacement.getOrderCount() ? existing : replacement
                ));

        return totalScanMap.entrySet().stream()
                .filter(entry -> orderMap.containsKey(entry.getKey())) // totalScanMap의 title로 orderMap filter처리
                .map(entry -> {
                    CustomerOrderTopInterface orderInfo = orderMap.get(entry.getKey());
                    return ResponseCustomerScanTopDto.builder()
                            .title(entry.getKey())
                            .totalScanCount(entry.getValue())
                            .totalOrderCount(orderInfo.getOrderCount())
                            .brandName(orderInfo.getBrandName())
                            .deliveryDate(orderInfo.getDeliveryDate())
                            .build();
                })
                .sorted(Comparator.comparingInt(ResponseCustomerScanTopDto::getTotalScanCount).reversed())  // totalScanCount로 내림차순 정렬
                .limit(100)  // 상위 100개만 가져오기
                .collect(Collectors.toList());
    }

    // 고객사 TOP
    public List<CustomerTopByNationInterface> getCustomerTop(Integer limit){

        return customerTopRepository.getCustomerTop(AESKEY, limit);
    }

    // TOP 고객사별 국가
    public List<NationTopByCustomerInterface> getNationTopByCustomer(String customerName) {
        return customerTopRepository.getNationTopByCustomer(AESKEY, customerName);
    }

    public List<CustomerScanPerPeriodTopInterface> getYearlyCustomerScanTop() {
        return customerTopPerPeriodRepository.getYearlyCustomerScanTop(AESKEY);
    }

    public List<CustomerScanPerPeriodTopInterface> getMonthlyCustomerTop() {
        return customerTopPerPeriodRepository.getMonthlyCustomerScanTop(AESKEY);
    }

    public List<CustomerScanPerPeriodTopInterface> getWeeklyCustomerTop() {
        return customerTopPerPeriodRepository.getWeeklyCustomerScanTop(AESKEY);
    }
}
