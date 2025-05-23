package com.cknb.htPlatform.auth.service;

import com.cknb.htPlatform.auth.repository.UserRepository;
import com.cknb.htPlatform.entity.DataReportUserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;


@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserService {

    private final UserRepository userRepository;

    public String createTokenForUser(String userNo) {
        String token = UUID.randomUUID().toString();
        Date expiryTime = new Date(System.currentTimeMillis() + 30 * 60 * 1000); // 30분

        // 사용자 정보 조회 또는 생성
        DataReportUserEntity entity = userRepository.findByUserNo(userNo);
        if (entity == null) {
            entity = new DataReportUserEntity();
            entity.setUserNo(userNo);
        }

        // 토큰 정보 업데이트
        entity.setToken(token);
        entity.setExpiry(expiryTime);
        userRepository.save(entity);

        return token;
    }

    public boolean validateToken(String userNo, String token) {
        DataReportUserEntity entity = userRepository.findByUserNoAndToken(userNo, token);

        // 토큰이 없거나 만료됨
        if (entity == null || new Date().after(entity.getExpiry())) {
            return false;
        }

        return true;
    }


}
