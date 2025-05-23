package com.cknb.htPlatform.scan.repository;


import com.cknb.htPlatform.entity.IqrDetInfoEntity;
import com.cknb.htPlatform.scan.dto.vo.LangScanInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LangScanRepository extends JpaRepository<IqrDetInfoEntity, Long> {
    @Query(value =
            " SELECT month_date AS monthDate " +
                    " 	,SUM(CASE WHEN lang IN ('cn', 'zh', 'tw', 'zh-hans') THEN count ELSE 0 END) AS cncount " +
                    " 	,SUM(CASE WHEN lang IN ('vi') THEN count ELSE 0 END) AS vicount " +
                    " 	,SUM(CASE WHEN lang IN ('en') THEN count ELSE 0 END) AS encount " +
                    " 	,SUM(CASE WHEN lang IN ('in','id') THEN count ELSE 0 END) AS incount " +
                    " 	,SUM(CASE WHEN lang IN ('ko') THEN count ELSE 0 END) AS kocount " +
                    " 	,SUM(CASE WHEN lang IN ('ru') THEN count ELSE 0 END) AS rucount " +
                    "     ,SUM(CASE WHEN lang IN ('jp', 'ja') THEN count ELSE 0 END) AS jpcount " +
                    " 	,SUM(CASE WHEN lang IN ('(null)','NULL') THEN count ELSE 0 END) AS non " +
                    " 	,SUM(CASE WHEN lang NOT IN ('cn', 'zh', 'tw','vi','en','in','id','ko','ru','jp', 'zh-hans', 'ja') THEN count ELSE 0 END) AS etc " +
                    " FROM month_scan_lang		 " +
                    " GROUP BY monthDate " +
                    " UNION ALL " +
                    " SELECT date_format(now(), '%Y-%m') AS monthDate " +
                    " 	,SUM(CASE WHEN a.lang IN ('cn', 'zh', 'tw', 'zh-hans') THEN a.count ELSE 0 END) AS cncount " +
                    " 	,SUM(CASE WHEN a.lang = 'vi' THEN a.count ELSE 0 END) AS vicount " +
                    " 	,SUM(CASE WHEN a.lang = 'en' THEN a.count ELSE 0 END) AS encount " +
                    " 	,SUM(CASE WHEN a.lang IN ('in', 'id') THEN a.count ELSE 0 END) AS incount " +
                    " 	,SUM(CASE WHEN a.lang = 'ko' THEN a.count ELSE 0 END) AS kocount " +
                    " 	,SUM(CASE WHEN a.lang = 'ru' THEN a.count ELSE 0 END) AS rucount " +
                    "     ,SUM(CASE WHEN a.lang IN ('jp', 'ja') THEN a.count ELSE 0 END) AS jpcount " +
                    " 	,SUM(CASE WHEN a.lang IN ('(null)','NULL') THEN a.count ELSE 0 END) AS non " +
                    " 	,SUM(CASE WHEN a.lang NOT IN ('cn', 'zh', 'tw','vi','en','in','id','ko','ru','jp', 'zh-hans', 'ja') THEN a.count ELSE 0 END) AS etc " +
                    " FROM " +
                    " 	(SELECT lang, count(lang) count " +
                    " 	FROM iqr_det_info " +
                    " 	WHERE det_date BETWEEN date_format(now(), '%Y-%m-01') AND last_day(now()) + INTERVAL 1 DAY - INTERVAL 1 SECOND " +
                    " 	GROUP BY lang) a ", nativeQuery = true
    )
    List<LangScanInterface> getLangScan();
}
