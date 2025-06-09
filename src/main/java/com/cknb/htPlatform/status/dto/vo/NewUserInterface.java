package com.cknb.htPlatform.status.dto.vo;

public interface NewUserInterface {
    String getUserRole();
    Integer getNewUserTotal();
    Integer getHidden();
    Integer getCop();
    Integer getGlobal();
    Integer getMissing(); // 옛날 global
    Integer getAppEtc(); // 3종 앱, missing 외
    Integer getAndroid();
    Integer getIos();
    Integer getOsEtc();
    Integer getEmailJoin();
    Integer getGoogleJoin();
    Integer getNaverJoin();
    Integer getKakaoJoin();
    Integer getFacebookJoin();
    Integer getAppleJoin();
    Integer getQqJoin();
    Integer getWechatJoin();
    Integer getLineJoin();
}
