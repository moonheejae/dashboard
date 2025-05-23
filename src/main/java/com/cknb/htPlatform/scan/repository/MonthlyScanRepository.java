package com.cknb.htPlatform.scan.repository;

import com.cknb.htPlatform.entity.IqrDetInfoEntity;
import com.cknb.htPlatform.scan.dto.vo.MonthlyScanInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonthlyScanRepository extends JpaRepository<IqrDetInfoEntity, Long> {
    @Query(value = """
        WITH RECURSIVE date_seq AS (
              SELECT DATE(CONCAT(:yearCheck, '-', :monthCheck, '-01')) AS dt
              UNION ALL
              SELECT DATE_ADD(dt, INTERVAL 1 DAY)
              FROM date_seq
              WHERE MONTH(DATE_ADD(dt, INTERVAL 1 DAY)) = :monthCheck
          )
          SELECT
              ds.dt AS monthDate,
              COUNT(
                      CASE
                          WHEN idi.no IS NOT NULL
                              AND isc.rnd_yn != 1
                              AND aesDecrypt(ic.title, :aesKey) != '콘텐츠 사업부'
                              AND aesDecrypt(ic.title, :aesKey) != 'CKNB(내부)'
                              AND (idd.market IS NULL OR idd.market NOT IN (12))
                              THEN idi.no
                          ELSE NULL
                          END
              ) AS scanCount
          FROM
              date_seq ds
                  LEFT JOIN iqr_det_info idi
                            ON DATE(idi.det_date) = ds.dt
                                AND MONTH(idi.det_date) = :monthCheck
                                AND YEAR(idi.det_date) = :yearCheck
                                AND idi.iqr_condition != 4
                                AND idi.idx != 518
                  LEFT JOIN iqr_service_config isc
                            ON idi.service_no = isc.no
                  LEFT JOIN iqr_customer ic
                            ON ic.no = isc.customer_cd
                  LEFT JOIN iqr_det_device idd 
                            ON idd.iqr_det_no = idi.no
          GROUP BY
              ds.dt
          ORDER BY
              ds.dt ASC;
    """, nativeQuery = true)
    List<MonthlyScanInterface> getMonthlyScan(@Param("aesKey") String aesKey, @Param("yearCheck") String yearCheck, @Param("monthCheck") String monthCheck);
}
