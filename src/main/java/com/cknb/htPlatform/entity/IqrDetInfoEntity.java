package com.cknb.htPlatform.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "iqr_det_info")
public class IqrDetInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;
    @Column
    private Long idx;
    @Column
    private Integer promotionType;
    @Column
    private LocalDateTime detTime;
    @Column
    private LocalDate detDate;
    @Column
    private String gps;
    @Column
    private String addressN;
    @Column
    private String addressA;
    @Column
    private String address;
    @Column
    private Integer deviceOs;
    @Column
    private String deviceImei;
    @Column
    private Long serviceNo;
    @Column
    private Integer iqrCondition;
    @Column
    private Integer serverGubun;
    @Column
    private Integer appGubun;
    @Column
    private String lang;
    @Column
    private String uniqTime;
}
