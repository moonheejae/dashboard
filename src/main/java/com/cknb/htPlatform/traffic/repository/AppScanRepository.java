package com.cknb.htPlatform.traffic.repository;

import com.cknb.htPlatform.entity.IqrDetInfoEntity;
import com.cknb.htPlatform.traffic.dto.vo.AppChinaScanInterface;
import com.cknb.htPlatform.traffic.dto.vo.AppScanInterface;
import com.cknb.htPlatform.traffic.dto.vo.FakeScanInfoInterface;
import com.cknb.htPlatform.traffic.dto.vo.ScanInfoInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppScanRepository extends JpaRepository<IqrDetInfoEntity, Long> {

    @Query(value = """
            select
            	sum(aos) as android,
            	sum(ios) as ios,
                sum(unu) as un,
                sum(non) as non
            from(
                 SELECT
                      count(CASE WHEN device_os = 1 THEN 1 END) as aos
                      ,count(CASE WHEN device_os = 2 THEN 1 END) as ios
                      ,count(CASE WHEN device_os NOT IN (1,2) THEN 1 END) AS unu
                      ,count(CASE WHEN device_os IS NULL THEN 1 END) AS non
                 FROM iqr_det_info a
                 LEFT JOIN iqr_det_device b ON b.iqr_det_no = a.no
                 WHERE a.det_date BETWEEN date_format(now(), '%Y-%m-01') AND last_day(now()) + INTERVAL 1 DAY - INTERVAL 1 second
                 UNION ALL
                 SELECT
                      sum(aos_count) as aos
                      ,sum(ios_count) as ios
                      ,un as unu
                      ,non as non
                 FROM month_scan_os
            ) total
            """, nativeQuery = true)
    AppScanInterface getAppScan();


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
            FROM iqr_det_info a
            		LEFT JOIN iqr_det_device b ON b.iqr_det_no = a.no
            		WHERE a.det_date BETWEEN date_format(now(), '%Y-%m-01') AND last_day(now()) + INTERVAL 1 DAY - INTERVAL 1 second

                UNION ALL

                SELECT
                    MSC.allcount + MSM.wechat         AS allcount,
                    MSC.threesixzero,
                    MSC.wandure,
                    MSC.yongbo,
                    MSC.xiaomi,
                    MSC.baidu,
                    MSC.oppo,
                    MSC.vivo,
                    MSC.anz,
                    MSC.huawei,
                    MSM.wechat                        AS wechat,
                    MSC.appchina,
                    MSC.kuan,
                    MSC.lenovo,
                    MSC.flyme,
                    MSC.twothreefourfive,
                    MSC.samsung,
                    MSC.honor
            		FROM month_scan_china MSC
            		inner join month_scan_market MSM
            		on MSC.month_date = MSM.month_date
            ) total_summary

            """, nativeQuery = true)
    AppChinaScanInterface getChinaScan();


    @Query(value = """
            SELECT
                COUNT(*) AS totalScanCount, -- 전체 스캔 수
                COUNT(CASE WHEN idi.iqr_condition = 2 THEN 1 END) AS fakeScanCount, -- 가품 수
                ROUND(
                    COUNT(CASE WHEN idi.iqr_condition = 2 THEN 1 END) / COUNT(*) * 100,
                    2
                ) AS fakeRatioPercent -- 가품 비율 (%)
            FROM iqr_det_info idi
            LEFT JOIN iqr_service_config isc ON idi.service_no = isc.no
            WHERE idi.det_date BETWEEN CURDATE() - INTERVAL (:selectedDays - 1) DAY AND CURDATE();
            """, nativeQuery = true)
    ScanInfoInterface getScanInfo(@Param("selectedDays") Integer selectedDays);


    @Query(value = """
           SELECT
                iqrDet.idx as idx,
                iqrDet.det_time as detTime,
                iqrDet.address_n as addressN,
                iqrService.codetitle as deliveryOrder, -- 발주 차수
                aesDecrypt(brandMaster.display_brand_ko, :aesKey) as brand,
                aesDecrypt(iqrCustomer.title, :aesKey) as customer
            FROM iqr_det_info iqrDet
            LEFT JOIN iqr_service_config iqrService ON iqrDet.service_no = iqrService.no
            LEFT JOIN
                iqr_customer iqrCustomer ON iqrCustomer.no = iqrService.customer_cd
            LEFT JOIN
                brand_master brandMaster ON iqrCustomer.no = brandMaster.customer_cd
            WHERE iqrDet.det_date BETWEEN CURDATE() - INTERVAL (:selectedDays - 1) DAY AND CURDATE()
            and iqrDet.iqr_condition = 2
            group by iqrdet.idx
            order by iqrDet.det_time desc;
            """, nativeQuery = true)
    List<FakeScanInfoInterface> getFakeScanInfo(@Param("aesKey") String aesKey, @Param("selectedDays") Integer selectedDays);


}
