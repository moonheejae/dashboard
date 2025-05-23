package com.cknb.htPlatform.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "iqr_det_daily")
public class IqrDetDailyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer no;
    @Column(name = "det_date")
    private LocalDateTime detDate;
    @Column(name = "nation")
    private String nation;
    @Column(name = "customer_cd")
    private Integer customerCd;
    @Column(name = "iqr_condition")
    private Integer iqrCondition;
    @Column(name = "det_count")
    private Integer detCount;

}
