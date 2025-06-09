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
@Table(name = "comm_review")
public class CommReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;
    @Column(name = "brand_no")
    private Long brandNo;
    @Column(name = "score")
    private Integer score;
    @Column(name = "brand_name")
    private String brandName;
    @Column(name = "content")
    private String content;
    @Column(name = "img_cnt")
    private Integer imgCnt;
    @Column(name = "category")
    private Integer category;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @Column(name = "comment_cnt")
    private Integer commentCnt;
    @Column(name = "like_cnt")
    private Integer likeCnt;
    @Column(name = "user_no")
    private Integer userNo;
    @Column(name = "lang")
    private String lang;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "declaration_no")
    private Integer declarationNo;
    @Column(name = "pageviews_cnt")
    private Integer pageviewsCnt;
}
