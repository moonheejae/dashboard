package com.cknb.htPlatform.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "china_market_registration_status")
public class ChinaMarketRegistrationStatusEntity {
    @Id
    private Integer no;
    @Column(name = "china_market_name")
    private String chinaMarketName;
    @Column(name = "hiddentag")
    private String hiddentag;
    @Column(name = "hiddentag_cop")
    private String hiddentagCop;
    @Column(name = "hiddentag_global")
    private String hiddentagGlobal;
    @Column(name = "last_upload_date")
    private LocalDateTime lastUploadDate;
    @Column(name = "market_share")
    private Float marketShare;
    @Column(name = "status")
    private String status;
    @Column(name = "content")
    private String content;
    @Column(name = "update_date")
    private LocalDateTime updateDate;
    @Column(name = "create_date")
    private LocalDateTime createDate;
}
