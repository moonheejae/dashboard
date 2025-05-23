package com.cknb.htPlatform.scan.repository;

import com.cknb.htPlatform.entity.IqrDetInfoEntity;
import com.cknb.htPlatform.scan.dto.vo.NationScanInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NationScanRepository extends JpaRepository<IqrDetInfoEntity, Long> {
    @Query(value =
            " SELECT month_date as monthDate" +
            " 		,SUM(CASE WHEN nation = '중국' THEN count ELSE 0 END) AS cncount " +
            " 		,SUM(CASE WHEN nation = '베트남' THEN count ELSE 0 END) AS vicount " +
            " 		,SUM(CASE WHEN nation = '인도네시아' THEN count ELSE 0 END) AS incount " +
            " 		,SUM(CASE WHEN nation = '대한민국' THEN count ELSE 0 END) AS kocount " +
            " 		,SUM(CASE WHEN nation = '말레이시아' THEN count ELSE 0 END) AS macount " +
            " 		,SUM(CASE WHEN nation = '태국' THEN count ELSE 0 END) AS thcount " +
            " 		,SUM(CASE WHEN nation = '필리핀' THEN count ELSE 0 END) AS phcount " +
            " 		,SUM(CASE WHEN nation = '대만' THEN count ELSE 0 END) AS twcount " +
            " 		,SUM(CASE WHEN nation = '일본' THEN count ELSE 0 END) AS jpcount " +
            " 		,SUM(CASE WHEN nation = '싱가포르' THEN count ELSE 0 END) AS sgcount " +
            " 		,SUM(CASE WHEN nation = '러시아' THEN count ELSE 0 END) AS rucount " +
            " 		,SUM(CASE WHEN nation NOT IN ('중국', '베트남', '인도네시아','대한민국','말레이시아','태국','필리핀','대만','일본','싱가포르','러시아') THEN count ELSE 0 END) AS etccount " +
            " FROM month_scan_nation		 " +
            " GROUP BY monthDate " +
            " UNION ALL " +
            " SELECT date_format(now(), '%Y-%m') AS month_date " +
            " 		,SUM(CASE WHEN a.address_n = '중국' THEN a.count ELSE 0 END) AS cncount " +
            " 		,SUM(CASE WHEN a.address_n = '베트남' THEN a.count ELSE 0 END) AS vicount " +
            " 		,SUM(CASE WHEN a.address_n = '인도네시아' THEN a.count ELSE 0 END) AS incount " +
            " 		,SUM(CASE WHEN a.address_n = '대한민국' THEN a.count ELSE 0 END) AS kocount " +
            " 		,SUM(CASE WHEN a.address_n = '말레이시아' THEN a.count ELSE 0 END) AS macount " +
            " 		,SUM(CASE WHEN a.address_n = '태국' THEN a.count ELSE 0 END) AS thcount " +
            " 		,SUM(CASE WHEN a.address_n = '필리핀' THEN a.count ELSE 0 END) AS phcount " +
            " 		,SUM(CASE WHEN a.address_n = '대만' THEN a.count ELSE 0 END) AS twcount " +
            " 		,SUM(CASE WHEN a.address_n = '일본' THEN a.count ELSE 0 END) AS jpcount " +
            " 		,SUM(CASE WHEN a.address_n = '싱가포르' THEN a.count ELSE 0 END) AS sgcount " +
            " 		,SUM(CASE WHEN a.address_n = '러시아' THEN a.count ELSE 0 END) AS rucount " +
            " 		,SUM(CASE WHEN a.address_n NOT IN ('중국', '베트남', '인도네시아','대한민국','말레이시아','태국','필리핀','대만','일본','싱가포르','러시아') THEN a.count ELSE 0 END) AS etccount " +
            " FROM " +
            " 	(SELECT a.address_n, count(a.address_n) count " +
            " 	from iqr_det_info a " +
            " 	WHERE a.det_date BETWEEN date_format(now(), '%Y-%m-01') AND last_day(now()) + INTERVAL 1 DAY - INTERVAL 1 SECOND " +
            " 	GROUP BY year(a.det_date), month(a.det_date), a.address_n) a ", nativeQuery = true)
    List<NationScanInterface> getNationScan();
}
