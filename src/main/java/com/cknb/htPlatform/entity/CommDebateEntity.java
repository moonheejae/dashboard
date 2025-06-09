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
@Table(name = "comm_debate")
public class CommDebateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;
    @Column(name = "user_no")
    private Long userNo;
    @Column(name = "name")
    private String name;
    @Column(name = "content")
    private String content;
    @Column(name = "category")
    private Long category;
    @Column(name = "agree_cnt")
    private Integer agreeCnt;
    @Column(name = "disagree_cnt")
    private Integer disagreeCnt;
    @Column(name = "img_cnt")
    private Integer imgCnt;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @Column(name = "comment_cnt")
    private Integer commentCnt;
    @Column(name = "lang")
    private String lang;
    @Column(name = "like_cnt")
    private Integer likeCnt;
    @Column(name = "declaration_no")
    private Long declarationNo;
    @Column(name = "pageviews_cnt")
    private Integer pageviewsCnt;
}
