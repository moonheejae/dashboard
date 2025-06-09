package com.cknb.htPlatform.status.repository;

import com.cknb.htPlatform.entity.UserMasterEntity;
import com.cknb.htPlatform.status.dto.vo.NewUserInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NewUserRepository extends JpaRepository<UserMasterEntity, Long> {
    @Query(value = """
             SELECT
                                        user_role AS userRole,
                                        COUNT(*) AS newUserTotal,
                                        SUM(CASE WHEN app_gubun = 1 THEN 1 ELSE 0 END) AS hidden,
                                        SUM(CASE WHEN app_gubun = 2 THEN 1 ELSE 0 END) AS cop,
                                        SUM(CASE WHEN app_gubun = 20 THEN 1 ELSE 0 END) AS global,
                                        SUM(CASE WHEN app_gubun = 0 THEN 1 ELSE 0 END) AS missing,
                                        SUM( case when app_gubun not in (0,1,2,20) then 1 else 0 end) appEtc,
                                        SUM(CASE WHEN device_os = 1 THEN 1 ELSE 0 END) AS android,
                                        SUM(CASE WHEN device_os = 2 THEN 1 ELSE 0 END) AS ios,
                                        SUM(CASE WHEN device_os not in (1,2) THEN 1 ELSE 0 END) AS osEtc,
                                        SUM( case when type = 0 then 1 else 0 end) emailJoin,
                                        SUM( case when type = 1 then 1 else 0 end) googleJoin,
                                        SUM( case when type = 2 then 1 else 0 end) naverJoin,
                                        SUM( case when type = 3 then 1 else 0 end) kakaoJoin,
                                        SUM( case when type = 4 then 1 else 0 end) facebookJoin,
                                        SUM( case when type = 5 then 1 else 0 end) appleJoin,
                                        SUM( case when type = 6 then 1 else 0 end) qQJoin,
                                        SUM( case when type = 7 then 1 else 0 end) wechatJoin,
                                        SUM( case when type = 8 then 1 else 0 end) lineJoin
                                    FROM user_master
                                    WHERE (user_role = "USER")
                                        AND create_date   >= CURDATE() - INTERVAL (:selectedDays - 1) day
            """, nativeQuery = true)
    NewUserInterface getNewUserCountAndJoinPath(@Param("selectedDays") Integer selectedDays);
}
