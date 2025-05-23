package com.cknb.htPlatform.auth.repository;

import com.cknb.htPlatform.entity.DataReportUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<DataReportUserEntity, Long> {
    DataReportUserEntity findByUserNo(@Param("user_no") String userNo);
    DataReportUserEntity findByUserNoAndToken(@Param("user_no") String userNo, @Param("token") String token);
}
