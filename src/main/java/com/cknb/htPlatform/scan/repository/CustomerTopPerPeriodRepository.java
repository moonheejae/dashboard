package com.cknb.htPlatform.scan.repository;

import com.cknb.htPlatform.entity.IqrDetDailyEntity;
import com.cknb.htPlatform.scan.dto.vo.CustomerScanPerPeriodTopInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerTopPerPeriodRepository extends JpaRepository<IqrDetDailyEntity, Integer> {

    @Query(value = """
            SELECT DATE_FORMAT(det_date, '%Y') as year
			 , DATE_FORMAT(det_date, '%m') as month
			 , AA.ranking as ranking
			 , AA.customer_cd as customerCd
			 , IFNULL(NULLIF(aesDecrypt(CC.display_brand_ko, :aesKey), ''), aesDecrypt(CC.display_name_ko,:aesKey)) AS customer
			 , SUM(det_count) count
		  FROM
			 (
				SELECT A.customer_cd
					 , A.count
					 , ROW_NUMBER() OVER (ORDER BY count DESC) AS ranking
				  FROM
					 (
						SELECT customer_cd
							 , SUM(det_count) AS count
						  FROM iqr_det_daily
						 WHERE DATE_FORMAT(det_date, '%Y%m') >= DATE_FORMAT(DATE_ADD(CURDATE(), INTERVAL -11 MONTH), '%Y%m')
						   AND customer_cd >= 21
						   AND customer_cd NOT IN (74, 78, 87)
					  GROUP BY customer_cd
					  ORDER BY count DESC
						 LIMIT 10
					 ) A
			 ) AA
	 LEFT JOIN iqr_det_daily BB
			ON AA.customer_cd = BB.customer_cd
	 LEFT JOIN brand_master CC
			ON CC.customer_cd = AA.customer_cd
		 WHERE DATE_FORMAT(det_date, '%Y%m') >= DATE_FORMAT(DATE_ADD(CURDATE(), INTERVAL -11 MONTH), '%Y%m')
	  GROUP BY ranking
			 , year
			 , month
""", nativeQuery = true)
    List<CustomerScanPerPeriodTopInterface> getYearlyCustomerScanTop(@Param("aesKey")String aesKey);


    @Query(value = """
            SELECT DATE_FORMAT(start_date, '%Y-%m-%d') AS startDate
                 , DATE_FORMAT(end_date, '%Y-%m-%d') AS endDate
                 , week
                 , ranking
                 , B.customer_cd AS customerCd
                 , IFNULL(NULLIF(aesDecrypt(C.display_brand_ko, :aesKey), ''), aesDecrypt(C.display_name_ko, :aesKey)) AS customer
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
                         , AA.customer_cd
                         , SUM(det_count) count
                      FROM
                         (
                            SELECT A.customer_cd
                                 , A.count
                                 , ROW_NUMBER() OVER (ORDER BY count DESC) AS ranking
                              FROM
                                 (
                                    SELECT customer_cd
                                         , SUM(det_count) AS count
                                      FROM iqr_det_daily
                                     WHERE det_date >= DATE_ADD(CURDATE(), INTERVAL -27 DAY)
                                       AND customer_cd >= 21
                                       AND customer_cd NOT IN (74, 78, 87)
                                  GROUP BY customer_cd
                                  ORDER BY count DESC
                                     LIMIT 10
                                 ) A
                         ) AA
                 LEFT JOIN iqr_det_daily BB
                        ON AA.customer_cd = BB.customer_cd
                     WHERE det_date >= DATE_ADD(CURDATE(), INTERVAL -27 DAY)
                     GROUP BY det_date
                         , customer_cd
                 ) B
                ON B.det_date >= A.start_date
               AND A.end_date >= B.det_date
         LEFT JOIN brand_master C
                ON C.customer_cd = B.customer_cd
          GROUP BY week
                 , B.customer_cd
          ORDER BY ranking
                 , week DESC
""", nativeQuery = true)
    List<CustomerScanPerPeriodTopInterface> getMonthlyCustomerScanTop(@Param("aesKey")String aeskey);


    @Query(value = """
        SELECT DATE_FORMAT(det_date, '%Y-%m-%d') detDate
			 , AA.ranking
			 , AA.customer_cd  AS customerCd
			 , IFNULL(NULLIF(aesDecrypt(CC.display_brand_ko, :aesKey), ''), aesDecrypt(CC.display_name_ko, :aesKey)) AS customer
			 , SUM(det_count) count
		  FROM
			 (
				SELECT A.customer_cd
					 , A.count
					 , ROW_NUMBER() OVER (ORDER BY count DESC) AS ranking
				  FROM
					 (
						SELECT customer_cd
							 , SUM(det_count) AS count
						  FROM iqr_det_daily
						 WHERE det_date >= DATE_ADD(CURDATE(), INTERVAL -6 DAY)
						   AND customer_cd >= 21
						   AND customer_cd NOT IN (74, 78, 87)
					  GROUP BY customer_cd
					  ORDER BY count DESC
						 LIMIT 10
					 ) A
             ) AA
         LEFT JOIN iqr_det_daily BB
                ON AA.customer_cd = BB.customer_cd
         LEFT JOIN brand_master CC
                ON CC.customer_cd = AA.customer_cd
             WHERE det_date >= DATE_ADD(CURDATE(), INTERVAL -6 DAY)
          GROUP BY ranking
                 , detDate
    """, nativeQuery = true)
    List<CustomerScanPerPeriodTopInterface> getWeeklyCustomerScanTop(@Param("aesKey")String aesKey);

}
