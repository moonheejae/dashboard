package com.cknb.htPlatform.scan.repository;

import com.cknb.htPlatform.entity.IqrDetInfoEntity;
import com.cknb.htPlatform.scan.dto.vo.AppScanInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppScanRepository extends JpaRepository<IqrDetInfoEntity, Long> {

    @Query(value = """
        SELECT
            month_date AS month,
            hidden_count as hidden,
            cop_count  as cop,
            global_count as global,
            wechat_count as wechat,
            total_count as total
        FROM
            month_scan_app
        WHERE
            month_date BETWEEN :startMonth AND :endMonth
        UNION ALL
        SELECT
            DATE_FORMAT(NOW(), '%Y-%m') AS month,
            SUM(CASE WHEN app_gubun = 1 THEN 1 ELSE 0 END) AS hidden,
            SUM(CASE WHEN app_gubun = 2 THEN 1 ELSE 0 END) AS cop,
            SUM(CASE WHEN app_gubun = 20 THEN 1 ELSE 0 END) AS global,
            SUM(CASE WHEN app_gubun = 10 THEN 1 ELSE 0 END) AS wechat,
            COUNT(*) AS total_count
        FROM
            iqr_det_info
        WHERE
            det_time BETWEEN DATE_FORMAT(NOW(), '%Y-%m-01 00:00:00')
                          AND DATE_FORMAT(DATE_ADD(LAST_DAY(NOW()), INTERVAL 1 DAY), '%Y-%m-%d 00:00:00') - INTERVAL 1 SECOND
        ORDER BY
            month ASC;
    """, nativeQuery = true)
    List<AppScanInterface> getAppScan(@Param("startMonth") String startMonth, @Param("endMonth") String endMonth);

}
