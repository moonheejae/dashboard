package com.cknb.htPlatform.scan.dto.vo;

public interface CustomerTopByNationInterface {

    Integer getCustomerCd();
    String getCustomer();
    Integer getNormalCount();
    Integer getExpireCount();
    Integer getTotalCount();
    Long getNormalRatio();
    Long getExpireRatio();
    String getLogoImg();
}
