package com.cknb.htPlatform.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jdk.jfr.Description;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Description("국가별 통계 테이블")
@Table(name = "month_scan_nation")
public class MonthScanNationEntity {
    @Id
    private Integer no;
    @Column(name = "month_date")
    private String monthDate;
    @Column(name = "nation")
    private String nation;
    @Column(name = "count")
    private String count;
}
