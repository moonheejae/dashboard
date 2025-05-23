package com.cknb.htPlatform.scan.repository;

import com.cknb.htPlatform.entity.IqrDetInfoEntity;
import com.cknb.htPlatform.scan.dto.vo.DeviceScanInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceScanRepository extends JpaRepository<IqrDetInfoEntity, Long> {

    @Query(value =
        """
        SELECT date_format(now(), '%Y-%m') AS monthDate
			  ,sum(a.s_cnt) AS allCount
			  ,count(a.d_cnt) AS deviceCount
			  ,count(CASE WHEN a.s_cnt=1 THEN 1 END) AS oneCount
			  ,count(a.d_cnt) - count(CASE WHEN a.s_cnt=1 THEN 1 END) AS manyCount
		FROM
			(select a.no,
			a.device_imei as d_cnt,
			count(CASE WHEN a.device_imei IS NULL THEN "null" ELSE a.device_imei END) AS s_cnt
			FROM iqr_det_info a
			WHERE a.det_date BETWEEN date_format(now(), '%Y-%m-01') AND last_day(now()) + INTERVAL 1 DAY - INTERVAL 1 second
			GROUP BY a.device_imei) a
		UNION ALL
		SELECT month_date AS monthDate
			  ,all_count AS allCount
			  ,device_count AS deviceCount
			  ,one_count AS oneCount
			  ,many_count AS manyCount
		FROM month_scan_device
		ORDER BY monthDate ASC
        """
            , nativeQuery = true)
    List<DeviceScanInterface> getDeviceScan();
}
