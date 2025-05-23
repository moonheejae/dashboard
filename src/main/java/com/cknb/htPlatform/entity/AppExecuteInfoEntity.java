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
@Description("앱 실행 정보")
@Table(name = "app_exe_info")
public class AppExecuteInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;
    @Column(name = "app_kind")
    private Integer appKind;
    @Column(name = "os")
    private Integer os;
    @Column(name = "exe_date")
    private LocalDateTime exeDate;
    @Column(name = "exe_time")
    private String exeTime;
    @Column(name = "exe_count")
    private Integer exeCount;
    @Column(name = "lang")
    private String lang;
    @Column(name = "updated_datetime")
    private LocalDateTime updatedDatetime;
}
