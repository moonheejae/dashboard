package com.cknb.htPlatform.entity;

import jakarta.persistence.*;
import jdk.jfr.Description;
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
@Description("마켓별 다운로드")
@Table(name = "market_download")
public class MarketDownloadEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;
    @Column(name = "app_kind")
    private Integer appKind;
    @Column(name = "total_download")
    private Integer totalDownload;
    @Column(name = "first_download")
    private Integer firstDownload;
    @Column(name = "re_download")
    private Integer reDownload;
    @Column(name = "device_os")
    private Integer deviceOs;
    @Column(name = "app_gubun")
    private Integer appGubun;
    @Column(name = "market")
    private Integer market;
    @Column(name = "create_date")
    private LocalDateTime createDate;
}
