package com.cknb.htPlatform.scan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseCustomerScanTopDto {
    private String monthDate;
    private Integer scanCount;
    private Integer totalScanCount;
    private String title;
    private Integer totalOrderCount;
    private String brandName;
    private String deliveryDate;
}
