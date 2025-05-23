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
@Table(name = "data_report_error_comment")
public class NoticeCommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "board_id")
    private Long boardId;

    @Column(name = "content")
    private String content;

    @Column(name = "creaet_user")
    private String createUser;

    @Column(name = "created_dt")
    private LocalDateTime createdDt;
}
