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
@Table(name = "user_master")
public class UserMasterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;
    @Column(name = "type")
    private Integer type;
    @Column(name = "id")
    @Convert(converter = AesConverter.class)
    private String id;
    @Column(name = "password")
    private String password;
    @Column(name = "att_push_yn")
    private Integer attPushYn;
    @Column(name = "device_imei")
    private String deviceImei;
    @Column(name = "device_os")
    private Integer deviceOs;
    @Column(name = "lang")
    private String lang;
    @Column(name = "user_nickname")
    @Convert(converter = AesConverter.class)
    private String userNickname;
    @Column(name = "user_birthyear")
    @Convert(converter = AesConverter.class)
    private String userBirthyear;
    @Column(name = "user_gender")
    @Convert(converter = AesConverter.class)
    private String userGender;
    @Column(name = "user_country")
    private String userCountry;
    @Column(name = "user_img")
    private String userImg;
    @Column(name = "user_customer")
    private Integer userCustomer;
    @Column(name = "total_point")
    private Integer totalPoint;
    @Column(name = "use_point")
    private Integer usePoint;
    @Column(name = "scheduled_point")
    private Integer scheduledPoint;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @Column(name = "user_like_category")
    private Integer userLikeCategory;
    @Column(name = "level_point")
    private Integer levelPoint;
    @Column(name = "act_point")
    private Integer actPoint;
    @Column(name = "dummy_data")
    private Integer dummyData;
    @Column(name = "badge_no")
    private Long badgeNo;
    @Column(name = "app_gubun")
    private Integer appGubun;
    @Column(name = "stampPage")
    private Integer stampPage;
    @Column(name = "user_token")
    private String userToken;
    @Column(name = "pwd_init_yn")
    private String pwdInitYn;
    @Column(name = "user_role")
    private String userRole;
    @Column(name = "snsuuid")
    private String snsuuid;
    @Column(name = "auto_login_yn")
    private String autoLoginYn;
    @Column(name = "alarm_Check")
    private String alarmCheck;

}
