package com.cknb.htPlatform.entity;


import com.cknb.htPlatform.common.AesConverter;
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
@Table(name = "app_exe_device")
public class AppExecuteDeviceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;
    @Column(name = "app_exe_no")
    private Long appExeNo;
    @Column(name = "os_version")
    private String osVersion;
    @Column(name = "model_name")
    private String modelName;
    @Column(name = "device_imei")
    private String deviceImei;
    @Column(name = "manufacturer")
    private String manufacturer;
    @Column(name = "gps")
    @Convert(converter = AesConverter.class)
    private String gps;
    @Column(name = "address_n")
    private String addressN;
    @Column(name = "address_a")
    private String addressA;
    @Column(name = "address")
    @Convert(converter = AesConverter.class)
    private String address;
    @Column(name = "app_version")
    private String appVersion;
    @Column(name = "market")
    private Integer market;
    @Column(name = "create_date")
    private LocalDateTime createDate;
}
