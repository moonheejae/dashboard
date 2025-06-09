package com.cknb.htPlatform.traffic.repository;

import com.cknb.htPlatform.entity.MarketDownloadEntity;
import com.cknb.htPlatform.traffic.dto.vo.AppDownloadInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AppDownloadRepository extends JpaRepository<MarketDownloadEntity, Long> {

    @Query(value = """
                 SELECT
                    SUM(CASE WHEN market in (1,2) AND app_gubun IN (1, 2, 20) THEN allcount ELSE 0 END) AS android,
                    SUM(CASE WHEN market = 0 AND app_gubun IN (1, 2, 20) THEN allcount ELSE 0 END) AS ios,
                    SUM(CASE WHEN market in (3,5,7,8,9,11,15,16,19,20,22) AND app_gubun  IN (1, 2, 20) THEN allcount ELSE 0 END) AS chinaTotal,
                    SUM(CASE WHEN market = 7 AND app_gubun  IN (1, 2, 20)  THEN allcount ELSE 0 END) as baidu,
                    SUM(CASE WHEN market = 9 AND app_gubun  IN (1, 2, 20) THEN allcount ELSE 0 END) AS vivo,
                    SUM(CASE WHEN market = 8 AND app_gubun  IN (1, 2, 20) THEN allcount ELSE 0 END) AS oppo,
                    SUM(CASE WHEN market = 11 AND app_gubun  IN (1, 2, 20) THEN allcount ELSE 0 END) AS huawei,
                    SUM(CASE WHEN market = 20 AND app_gubun  IN (1, 2, 20) THEN allcount ELSE 0 END) AS xiaomi,
                    SUM(CASE WHEN market = 3 AND app_gubun  IN (1, 2, 20) THEN allcount ELSE 0 END) AS threesixzero,
                    SUM(CASE WHEN market = 19 AND app_gubun  IN (1, 2, 20) THEN allcount ELSE 0 END) AS wandure,
                    SUM(CASE WHEN market = 16 AND app_gubun  IN (1, 2, 20) THEN allcount ELSE 0 END) AS flyme,
                    SUM(CASE WHEN market = 5 AND app_gubun  IN (1, 2, 20) THEN allcount ELSE 0 END) AS yongbo,
                    SUM(CASE WHEN market = 15 AND app_gubun  IN (1, 2, 20) THEN allcount ELSE 0 END) AS lenovo,
                    SUM(CASE WHEN market = 22  AND app_gubun  IN (1, 2, 20) THEN allcount ELSE 0 END) AS honor
                FROM
                    (SELECT DATE_FORMAT(create_date, '%Y-%m') AS create_date, market, app_gubun, sum(total_download) AS allcount
                    from market_download
                    GROUP BY YEAR(create_date), MONTH(create_date), app_gubun, market) a
    """, nativeQuery = true)
    AppDownloadInterface getDownload();
}
