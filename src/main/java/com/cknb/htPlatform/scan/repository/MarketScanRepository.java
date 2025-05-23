package com.cknb.htPlatform.scan.repository;

import com.cknb.htPlatform.entity.IqrDetInfoEntity;
import com.cknb.htPlatform.scan.dto.vo.MarketScanInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MarketScanRepository extends JpaRepository<IqrDetInfoEntity, Long> {

    @Query( value =
            " SELECT date_format(now(), '%Y-%m') AS monthDate " +
            " 	  ,count(CASE WHEN b.market = 1 THEN 1 END) AS playStore " +
            " 	  ,count(CASE WHEN b.market = 0 THEN 1 END) AS appStore " +
            " 	  ,count(CASE WHEN b.market = 2 THEN 1 END) AS oneStore " +
            " 	  ,sum(CASE WHEN b.market NOT IN (0,1,2,12,99) THEN 1 END) AS chinaStore " +
            " 	  ,count(CASE WHEN b.market = 12 THEN 1 END) AS wechat " +
            " 	  ,count(CASE WHEN b.market IS NULL THEN 1 END) AS non " +
            " 	  ,count(CASE WHEN b.market = 99 THEN 1 END) AS un " +
            " FROM iqr_det_info a " +
            " LEFT JOIN iqr_det_device b ON b.iqr_det_no = a.no " +
            " WHERE a.det_date BETWEEN date_format(now(), '%Y-%m-01') AND last_day(now()) + INTERVAL 1 DAY - INTERVAL 1 second " +
            " UNION ALL " +
            " SELECT month_date AS monthDate" +
            "         ,play_store AS playStore " +
            "         ,app_store AS appStore " +
            "         ,one_store AS oneStore " +
            "         ,china_store AS chinaStore " +
            "         ,wechat " +
            "         ,non " +
            "         ,un " +
            " FROM month_scan_market " +
            " ORDER BY monthDate ASC ", nativeQuery = true)
    List<MarketScanInterface> getMarketScan();
}
