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
@Table(name = "pre_landing_page_answer")
public class PreLandingPageAnswerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;
    @Column
    private String conditionCheck;
    @Column
    private Integer pageNo;
    @Column
    private String gender;
    @Column
    private String age;
    @Column
    private String device_imei;
    @Column
    private String answer1;
    @Column
    private String answer2;
    @Column
    private String answer3;
    @Column
    private LocalDateTime createDate;
    @Column
    private Integer iqrNo;
    @Column
    private String address;
}
