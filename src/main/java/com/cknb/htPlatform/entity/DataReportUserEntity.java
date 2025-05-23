package com.cknb.htPlatform.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "data_report_user")
public class DataReportUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;
    @Column(name = "session_id")
    private String sessionId;
    @Column(name = "user_no")
    private String userNo;
    @Column(name = "token")
    private String token;
    @Column(name = "expiry")
    private Date expiry;
}
