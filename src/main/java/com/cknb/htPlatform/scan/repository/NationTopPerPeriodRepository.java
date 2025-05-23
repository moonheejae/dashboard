package com.cknb.htPlatform.scan.repository;

import com.cknb.htPlatform.entity.IqrDetDailyEntity;
import com.cknb.htPlatform.scan.dto.vo.NationScanPerPeriodTopInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NationTopPerPeriodRepository extends JpaRepository<IqrDetDailyEntity, Integer> {

    @Query(value =  """
    SELECT DATE_FORMAT(det_date, '%Y') AS year 
                    , DATE_FORMAT(det_date, '%m') AS month 
                    , AA.ranking AS ranking 
                    , AA.nation AS nation 
                    , SUM(det_count) AS count 
                  FROM 
                    ( 
                      SELECT A.nation AS nation
                          , A.count 
                          , ROW_NUMBER() OVER (ORDER BY count DESC) AS ranking 
                        FROM 
                          ( 
                            SELECT nation 
                                , SUM(det_count) AS count 
                              FROM iqr_det_daily 
                             WHERE DATE_FORMAT(det_date, '%Y%m') >= DATE_FORMAT(DATE_ADD(CURDATE(), INTERVAL -11 MONTH), '%Y%m') 
                              AND nation != '없음' 
                           GROUP BY nation 
                           ORDER BY count DESC 
                             LIMIT 10 
                          ) A 
                    ) AA 
              LEFT JOIN iqr_det_daily BB 
                   ON AA.nation = BB.nation 
                 WHERE DATE_FORMAT(BB.det_date, '%Y%m') >= DATE_FORMAT(DATE_ADD(CURDATE(), INTERVAL -11 MONTH), '%Y%m') 
               GROUP BY ranking 
                    , year 
                    , month 
                    , nation 
    """ , nativeQuery = true)
    List<NationScanPerPeriodTopInterface> getYearlyNationScanTop();

    @Query(value = """
         SELECT DATE_FORMAT(start_date, '%Y-%m-%d') AS startDate
         			 , DATE_FORMAT(end_date, '%Y-%m-%d') AS endDate
         			 , week
         			 , ranking
         			 , B.nation
         			 , SUM(IFNULL(count, 0)) count
         		  FROM
         			 (
         				SELECT 4 AS week
         					 , DATE_ADD(CURDATE(), INTERVAL -27 day) AS start_date
         					 , DATE_ADD(CURDATE(), INTERVAL -21 day) AS end_date
         			 UNION ALL
         				SELECT 3
         					 , DATE_ADD(CURDATE(), INTERVAL -20 day)
         					 , DATE_ADD(CURDATE(), INTERVAL -14 day)
         			 UNION ALL
         				SELECT 2
         					 , DATE_ADD(CURDATE(), INTERVAL -13 day)
         					 , DATE_ADD(CURDATE(), INTERVAL -7 day)
         			 UNION ALL
         				SELECT 1
         					 , DATE_ADD(CURDATE(), INTERVAL -6 day)
         					 , CURDATE()
         			 ) A
         	 LEFT JOIN
         			 (
         				SELECT det_date
         					 , AA.ranking
         					 , AA.nation
         					 , SUM(det_count) count
         				  FROM
         					 (
         						SELECT A.nation
         							 , A.count
         							 , ROW_NUMBER() OVER (ORDER BY count DESC) AS ranking
         						  FROM
         							 (
         								SELECT nation
         									 , SUM(det_count) AS count
         								  FROM iqr_det_daily
         								 WHERE det_date >= DATE_ADD(CURDATE(), INTERVAL -27 DAY)
         								   AND nation != '없음'
         							  GROUP BY nation
         							  ORDER BY count DESC
         								 LIMIT 10
         							 ) A
         					 ) AA
         			 LEFT JOIN iqr_det_daily BB
         					ON AA.nation = BB.nation
         				 WHERE det_date >= DATE_ADD(CURDATE(), INTERVAL -27 DAY)
         			  GROUP BY det_date
         					 , nation
         			 ) B
         			ON B.det_date >= A.start_date
         		   AND A.end_date >= B.det_date
         	  GROUP BY week
         			 , B.nation
         	  ORDER BY ranking
         			 , week DESC
    """, nativeQuery = true)
    List<NationScanPerPeriodTopInterface> getMonthlyNationTop();

    @Query(value = """
         SELECT DATE_FORMAT(det_date, '%Y-%m-%d') AS detDate
                 , AA.ranking AS ranking
                 , AA.nation AS nation
                 , SUM(det_count) AS count
              FROM
                 (
                    SELECT A.nation
                         , A.count
                         , ROW_NUMBER() OVER (ORDER BY count DESC) AS ranking
                      FROM
                         (
                            SELECT nation
                                 , SUM(det_count) AS count
                              FROM iqr_det_daily
                             WHERE det_date >= DATE_ADD(CURDATE(), INTERVAL -6 DAY)
                               AND nation != '없음'
                          GROUP BY nation
                          ORDER BY count DESC
                             LIMIT 10
                         ) A
                 ) AA
         LEFT JOIN iqr_det_daily BB
                ON AA.nation = BB.nation
             WHERE det_date >= DATE_ADD(CURDATE(), INTERVAL -6 DAY)
          GROUP BY ranking
                 , detDate		
    """, nativeQuery = true)
    List<NationScanPerPeriodTopInterface> getWeeklyNationTop();




}
