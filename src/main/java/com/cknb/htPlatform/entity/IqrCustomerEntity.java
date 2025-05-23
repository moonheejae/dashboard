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
@Table(name = "iqr_customer")
public class IqrCustomerEntity {
    @Id
    private Integer no;
    @Column(name = "title")
    @Convert(converter = AesConverter.class)
    private String title;
    @Column(name = "biznumber")
    private Integer bizNumber;
    @Column(name= "person")
    @Convert(converter = AesConverter.class)
    private String person;
    @Column(name = "head_person")
    @Convert(converter = AesConverter.class)
    private String headPerson;
    @Column(name = "tel_upper")
    @Convert(converter = AesConverter.class)
    private String telUpper;
    @Column(name = "tel_middle")
    @Convert(converter = AesConverter.class)
    private String telMiddle;
    @Column(name = "tel_lower")
    @Convert(converter = AesConverter.class)
    private String telLower;
    @Column(name = "email")
    @Convert(converter = AesConverter.class)
    private String email;
    @Column(name = "address")
    @Convert(converter = AesConverter.class)
    private String address;
    @Column(name = "bus_person")
    @Convert(converter = AesConverter.class)
    private String busPerson;
    @Column(name = "design_person")
    @Convert(converter = AesConverter.class)
    private String designPerson;
    @Column(name = "memo")
    private String memo;
    @Column(name = "count")
    private Integer count;
    @Column(name = "wcount")
    private Integer wcount;
    @Column(name = "m_count")
    private Integer mCount;
    @Column(name = "m_wcount")
    private Integer mWcount;
    @Column(name = "service_start_dt")
    private LocalDateTime serviceStartDt;
    @Column(name = "product_family_code")
    private Integer productFamilyCode;
    @Column(name = "service_yn")
    private Integer serviceYn;
    @Column(name = "sales_status")
    private Integer salesStatus;
    @Column(name = "sales_inout")
    private Integer salesInout;
    @Column(name = "sales_gubun")
    private Integer salesGubun;
    @Column(name = "first_contact")
    private LocalDateTime firstContact;
    @Column(name = "last_contact")
    private LocalDateTime lastContact;
    @Column(name = "create_id")
    private Long createId;
    @Column(name = "status_board_no")
    private Long statusBoardNo;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @Column(name = "img_path")
    private String imgPath;
    @Column(name = "brand_url_ko")
    private String brandUrlKo;
    @Column(name = "brand_url_en")
    private String brandUrlEn;
    @Column(name = "brand_url_cn")
    private String brandUrlCn;
    @Column(name = "brand_url_jp")
    private String brandUrlJp;
    @Column(name = "brand_url_th")
    private String brandUrlTh;
    @Column(name = "brand_url_vi")
    private String brandUrlVi;
    @Column(name = "country_code")
    private String countryCode;
    @Column(name = "alarmy_use_gubun")
    private Integer alarmyUseGubun;
    @Column(name = "alarmy_company_email")
    @Convert(converter = AesConverter.class)
    private String alarmyCompanyEmail;
    @Column(name = "alarmy_use_date")
    private LocalDateTime alarmyUseDate;
    @Column(name = "update_date")
    private LocalDateTime updateDate;
}
