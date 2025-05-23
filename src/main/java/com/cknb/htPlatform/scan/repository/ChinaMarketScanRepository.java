package com.cknb.htPlatform.scan.repository;

import com.cknb.htPlatform.entity.IqrDetInfoEntity;
import com.cknb.htPlatform.scan.dto.vo.ChinaMarketScanInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChinaMarketScanRepository extends JpaRepository<IqrDetInfoEntity, Long> {

	@Query(value = """
        SELECT date_format(now(), '%Y-%m') AS monthDate
			 ,sum(CASE WHEN b.market NOT IN (0,1,2,99) THEN 1 END) AS allcount
			 ,COALESCE(sum(CASE WHEN b.market=11 THEN 1 END), 0) AS huawei
			 ,COALESCE(sum(CASE WHEN b.market=9 THEN 1 END), 0) AS vivo 
			 ,COALESCE(sum(CASE WHEN b.market=8 THEN 1 END), 0) AS oppo
			 ,COALESCE(sum(CASE WHEN b.market=7 THEN 1 END), 0) AS baidu
			 ,COALESCE(sum(CASE WHEN b.market=5 THEN 1 END), 0) AS yongbo
			 ,COALESCE(sum(CASE WHEN b.market=4 THEN 1 END), 0) AS wandure
			 ,COALESCE(sum(CASE WHEN b.market=3 THEN 1 END), 0) AS threesixzero
			 ,COALESCE(sum(CASE WHEN b.market=6 THEN 1 END), 0) AS xiaomi
			 ,COALESCE(sum(CASE WHEN b.market=16 THEN 1 END), 0) AS flyme
			 ,COALESCE(sum(CASE WHEN b.market=15 THEN 1 END), 0) AS lenovo
			 ,COALESCE(sum(CASE WHEN b.market=10 THEN 1 END), 0) AS anz
			 ,COALESCE(sum(CASE WHEN b.market=13 THEN 1 END), 0) AS appchina
			 ,COALESCE(sum(CASE WHEN b.market=14 THEN 1 END), 0) AS kuan
			 ,COALESCE(sum(CASE WHEN b.market =12 THEN 1 END), 0) AS wechat
			 ,COALESCE(sum(CASE WHEN market=17 THEN 1 END), 0)   AS twothreefourfive
			 ,COALESCE(sum(CASE WHEN market=18 THEN 1 END), 0)   AS samsung
			 ,COALESCE(sum(CASE WHEN market=19 THEN 1 END), 0)   AS honor
		FROM iqr_det_info a
		LEFT JOIN iqr_det_device b ON b.iqr_det_no = a.no
		WHERE a.det_date BETWEEN date_format(now(), '%Y-%m-01') AND last_day(now()) + INTERVAL 1 DAY - INTERVAL 1 second
	    UNION ALL
		SELECT  MSC.month_date AS monthDate
			   ,MSC.allcount + MSM.wechat allcount 
		       ,MSC.huawei 		  
		       ,MSC.vivo          
		       ,MSC.oppo          
		       ,MSC.baidu         
		       ,MSC.yongbo        
		       ,MSC.wandure       
		       ,MSC.threesixzero  
		       ,MSC.xiaomi        
		       ,MSC.flyme         
		       ,MSC.lenovo 		  
		       ,MSC.anz 		  
		       ,MSC.appchina      
		       ,MSC.kuan 		  
		       ,MSM.wechat  	  
		       ,MSC.twothreefourfive 
			   ,MSC.samsung 	  
			   ,MSC.honor 	      
		FROM month_scan_china MSC
		inner join month_scan_market MSM
		on MSC.month_date = MSM.month_date
		ORDER BY monthDate asc
            """, nativeQuery = true)
	List<ChinaMarketScanInterface> getChinaMarketScan();
}
