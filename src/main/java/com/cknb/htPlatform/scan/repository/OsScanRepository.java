package com.cknb.htPlatform.scan.repository;

import com.cknb.htPlatform.entity.IqrDetInfoEntity;
import com.cknb.htPlatform.scan.dto.vo.OsScanInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OsScanRepository extends JpaRepository<IqrDetInfoEntity, Long> {
    @Query(value =
            " SELECT date_format(now(), '%Y-%m') AS monthDate " +
            " 	  ,count(CASE WHEN device_os = 1 THEN 1 END) as aosCount  " +
            " 	  ,count(CASE WHEN device_os = 2 THEN 1 END) as iosCount " +
            " 	  ,count(CASE WHEN device_os NOT IN (1,2) THEN 1 END) AS unCount " +
            " 	  ,count(CASE WHEN device_os IS NULL THEN 1 END) AS nonCount " +
            " FROM iqr_det_info a " +
            " LEFT JOIN iqr_det_device b ON b.iqr_det_no = a.no " +
            " WHERE a.det_date BETWEEN date_format(now(), '%Y-%m-01') AND last_day(now()) + INTERVAL 1 DAY - INTERVAL 1 second " +
            " UNION ALL  " +
            " SELECT month_date AS monthDate " +
            " 	  ,aos_count as aosCount " +
            " 	  ,ios_count as iosCount " +
            " 	  ,un as unCount " +
            " 	  ,non as nonCount" +
            " FROM month_scan_os " +
            " ORDER BY monthDate ASC ", nativeQuery = true)
    List<OsScanInterface> getOsScan();
}
