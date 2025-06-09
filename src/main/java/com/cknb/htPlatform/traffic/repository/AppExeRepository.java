package com.cknb.htPlatform.traffic.repository;

import com.cknb.htPlatform.entity.AppExecuteDeviceEntity;
import com.cknb.htPlatform.traffic.dto.vo.AppChinaExeInterface;
import com.cknb.htPlatform.traffic.dto.vo.AppExeInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AppExeRepository extends JpaRepository<AppExecuteDeviceEntity, Long> {

    @Query(value = """
                        SELECT
                            SUM(aos) AS android,
                            SUM(ios) AS ios,
                            SUM(un) AS un,
                            SUM(non) AS non
                        FROM (
                            -- 이번 달 데이터
                            SELECT
                                SUM(CASE WHEN os = 1 THEN 1 END) AS aos,
                                SUM(CASE WHEN os = 2 THEN 1 END) AS ios,
                                SUM(CASE WHEN os NOT IN (1,2) THEN 1 END) AS un,
                                SUM(CASE WHEN os IS NULL THEN 1 END) AS non
                            FROM app_exe_device aed
                            LEFT JOIN app_exe_info aei ON aed.app_exe_no = aei.no
                            WHERE create_date BETWEEN DATE_FORMAT(NOW(), '%Y-%m-01')
                                                  AND LAST_DAY(NOW()) + INTERVAL 1 DAY - INTERVAL 1 SECOND
                        
                            UNION ALL
                        
                            -- 이전 달 누적 데이터
                            SELECT
                                SUM(aos_count) AS aos,
                                SUM(ios_count) AS ios,
                                SUM(un) AS un,
                	  	        SUM(non) as non
                            FROM month_exe_os
                        ) total_data;
            """, nativeQuery = true)
    AppExeInterface getExe();


    @Query(value = """
            SELECT
                SUM(allcount)           AS chinaTotal,
                SUM(threesixzero)       AS threesixzero,
                SUM(wandure)            AS wandure,
                SUM(yongbo)             AS yongbo,
                SUM(xiaomi)             AS xiaomi,
                SUM(baidu)              AS baidu,
                SUM(oppo)               AS oppo,
                SUM(vivo)               AS vivo,
                SUM(anz)                AS anz,
                SUM(huawei)             AS huawei,
                SUM(wechat)             AS wechat,
                SUM(appchina)           AS appchina,
                SUM(kuan)               AS kuan,
                SUM(lenovo)             AS lenovo,
                SUM(flyme)              AS flyme,
                SUM(twothreefourfive)   AS twothreefourfive,
                SUM(samsung)            AS samsung,
                SUM(honor)              AS honor
            FROM (
                -- 이번 달 데이터
                SELECT
                    SUM(CASE WHEN market NOT IN (0,1,2,99) THEN 1 END) AS allcount,
                    SUM(CASE WHEN market = 3 THEN 1 END)  AS threesixzero,
                    SUM(CASE WHEN market = 4 THEN 1 END)  AS wandure,
                    SUM(CASE WHEN market = 5 THEN 1 END)  AS yongbo,
                    SUM(CASE WHEN market = 6 THEN 1 END)  AS xiaomi,
                    SUM(CASE WHEN market = 7 THEN 1 END)  AS baidu,
                    SUM(CASE WHEN market = 8 THEN 1 END)  AS oppo,
                    SUM(CASE WHEN market = 9 THEN 1 END)  AS vivo,
                    SUM(CASE WHEN market = 10 THEN 1 END) AS anz,
                    SUM(CASE WHEN market = 11 THEN 1 END) AS huawei,
                    SUM(CASE WHEN market = 12 THEN 1 END) AS wechat,
                    SUM(CASE WHEN market = 13 THEN 1 END) AS appchina,
                    SUM(CASE WHEN market = 14 THEN 1 END) AS kuan,
                    SUM(CASE WHEN market = 15 THEN 1 END) AS lenovo,
                    SUM(CASE WHEN market = 16 THEN 1 END) AS flyme,
                    SUM(CASE WHEN market = 17 THEN 1 END) AS twothreefourfive,
                    SUM(CASE WHEN market = 18 THEN 1 END) AS samsung,
                    SUM(CASE WHEN market = 19 THEN 1 END) AS honor
                FROM app_exe_device aed
                LEFT JOIN app_exe_info aei ON aed.app_exe_no = aei.no
                WHERE create_date BETWEEN DATE_FORMAT(NOW(), '%Y-%m-01')
                                      AND LAST_DAY(NOW()) + INTERVAL 1 DAY - INTERVAL 1 SECOND
            
                UNION ALL
            
                -- 이전 월 데이터
                SELECT
                    MEC.allcount + MEM.wechat         AS allcount,
                    MEC.threesixzero,
                    MEC.wandure,
                    MEC.yongbo,
                    MEC.xiaomi,
                    MEC.baidu,
                    MEC.oppo,
                    MEC.vivo,
                    MEC.anz,
                    MEC.huawei,
                    MEM.wechat                        AS wechat,
                    MEC.appchina,
                    MEC.kuan,
                    MEC.lenovo,
                    MEC.flyme,
                    MEC.twothreefourfive,
                    MEC.samsung,
                    MEC.honor
                FROM month_exe_china MEC
                INNER JOIN month_exe_market MEM ON MEC.month_date = MEM.month_date
            ) total_summary;
            """, nativeQuery = true)
    AppChinaExeInterface getChinaExe();
}
