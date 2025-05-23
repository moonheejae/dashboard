package com.cknb.htPlatform.scan.repository;

import com.cknb.htPlatform.entity.IqrCustomerEntity;
import com.cknb.htPlatform.scan.dto.vo.CustomerOrderTopInterface;
import com.cknb.htPlatform.scan.dto.vo.CustomerScanTopInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<IqrCustomerEntity, Integer> {


    @Query(value = """
	SELECT *
   		FROM
   			(SELECT date_format(now(), '%Y-%m') AS monthDate
   	  				,aesDecrypt(ic.title, :aesKey) AS title
   			        ,aesDecrypt(bm.display_brand_ko, :aesKey) AS brandName
   	  				,count(*) AS scanCount
   			FROM iqr_det_info a
   			LEFT JOIN iqr_service_config c ON a.service_no = c.no
   			LEFT JOIN iqr_customer ic ON ic.no = c.customer_cd
   			LEFT JOIN brand_master bm ON ic.no = bm.customer_cd
   			WHERE a.det_date BETWEEN date_format(now(), '%Y-%m-01') AND last_day(now()) + INTERVAL 1 DAY - INTERVAL 1 SECOND
   			AND aesDecrypt(ic.title, :aesKey) != '콘텐츠 사업부'
   			AND aesDecrypt(ic.title, :aesKey) != 'CKNB(내부)'
   			GROUP BY aesDecrypt(ic.title, :aesKey)
   			ORDER BY scanCount DESC
   			LIMIT 100) b
   		UNION ALL
   		SELECT m.month_date AS monthDate
   	  		,m.customer_title AS title
            ,aesDecrypt(bm.display_brand_ko, :aesKey) AS brandName
   	  		,m.scan_count AS scanCount
   		FROM month_scan_customertop m
   		LEFT JOIN
   			iqr_customer ic ON m.customer_title = aesDecrypt(ic.title, :aesKey)
   		LEFT JOIN
   			brand_master bm ON ic.no = bm.customer_cd
   		WHERE (:startDate IS NULL OR :startDate = '' OR :endDate IS NULL OR :endDate = '' OR month_date >= :startDate AND month_date <= :endDate)
   		ORDER BY monthDate ASC
    """, nativeQuery = true)
    List<CustomerScanTopInterface> getCustomerScanTop(@Param("aesKey")String aesKey, @Param("startDate")String startDate, @Param("endDate")String endDate);


	@Query(value = """
	SELECT aesDecrypt(ic.title, :aesKey) title
	     , sum(di.delivery_amt) orderCount
 	     , aesDecrypt(bm.display_brand_ko , :aesKey ) brandName
	 	 , di.delivery_date deliveryDate
		FROM iqr_customer ic
		LEFT JOIN delivery_info di ON ic.no = di.customer_cd
		left join brand_master bm on ic.no = bm.customer_cd
        WHERE di.delivery_amt NOT IN ("NULL", 0)
            AND (:startDate IS NULL OR :startDate = '' OR :endDate IS NULL OR :endDate = '' OR
                 di.delivery_date BETWEEN CONCAT(:startDate, '-01') AND last_day(STR_TO_DATE(CONCAT(:endDate, '-01'), '%Y-%m-%d')))
		GROUP BY ic.title
""", nativeQuery = true)
	List<CustomerOrderTopInterface> getCustomerOrderTop(@Param("aesKey")String aesKey, @Param("startDate")String startDate, @Param("endDate")String endDate);

}
