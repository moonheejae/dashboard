package com.cknb.htPlatform.scan.dto.vo;

public interface NationTopByCustomerInterface {
    String getAddressN();
    Integer getNormalCount();
    Integer getExpireCount();
    Integer getTotalCount();
    Float getNormalRatio();
    Float getExpireRatio();
    String getFlagImg();
}
