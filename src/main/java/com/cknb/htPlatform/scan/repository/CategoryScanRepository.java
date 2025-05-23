package com.cknb.htPlatform.scan.repository;

import com.cknb.htPlatform.entity.IqrDetDailyEntity;
import com.cknb.htPlatform.scan.dto.vo.CategoryScanInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryScanRepository  extends JpaRepository<IqrDetDailyEntity, Integer> {

    @Query(value = """
                        SELECT
                            month_date as monthDate,
                            cosmetic,
                            fashion,
                            electronic,
                            medical,
                            generals,
                            others,
                            living,
                            baby,
                            beauty_device as beautyDevice,
                            documents,
                            manufacture,
                            foods,
                            functional_beauty as functionalBeauty,
                            total_care as totalCare,
                            total
                        FROM
                            month_scan_category
                        WHERE
                            month_date BETWEEN :startMonth AND :endMonth
                        GROUP BY monthDate
                    UNION ALL
                        SELECT
                            DATE_FORMAT(NOW(), '%Y-%m') AS monthDate,
                            sum(CASE WHEN cm.title_ko = '코스메틱' then 1 else 0 end) as cosmetic,
                            sum(CASE WHEN cm.title_ko = '패션' then 1 else 0 end) as fashion,
                            sum(CASE WHEN cm.title_ko = '전자제품' then 1 else 0 end) as electronic,
                            sum(CASE WHEN cm.title_ko = '의학' then 1 else 0 end) as medical,
                            sum(CASE WHEN cm.title_ko = '잡화' then 1 else 0 end) as generals,
                            sum(CASE WHEN cm.title_ko = '기타' then 1 else 0 end) as others,
                            sum(CASE WHEN cm.title_ko = '리빙' then 1 else 0 end) as cosmetic,
                            sum(CASE WHEN cm.title_ko = '유아용품' then 1 else 0 end) as living,
                            sum(CASE WHEN cm.title_ko = '미용기기' then 1 else 0 end) as baby,
                            sum(CASE WHEN cm.title_ko = '보증·관리' then 1 else 0 end) as beautyDevice,
                            sum(CASE WHEN cm.title_ko = '제조·산업' then 1 else 0 end) as documents,
                            sum(CASE WHEN cm.title_ko = '식품' then 1 else 0 end) as manufacture,
                            sum(CASE WHEN cm.title_ko = '기능성뷰티' then 1 else 0 end) as foods,
                            sum(CASE WHEN cm.title_ko = '토탈케어' then 1 else 0 end) as functionalBeauty,
                            sum(CASE WHEN cm.title_ko = '코스식품메틱' then 1 else 0 end) as totalCare
                        FROM iqr_det_info a
                        LEFT JOIN iqr_service_config c ON a.service_no = c.no
                        LEFT JOIN iqr_customer ic ON ic.no = c.customer_cd
                        LEFT JOIN category_info ci ON ic.no = ci.customer_cd
                        LEFT JOIN category_master cm ON cm.no = ci.category_cd
                        WHERE a.det_date BETWEEN DATE_FORMAT(NOW(), '%Y-%m-01')
                                              AND LAST_DAY(NOW()) + INTERVAL 1 DAY - INTERVAL 1 SECOND
                        AND aesDecrypt(ic.title, :aesKey) != '콘텐츠 사업부'
                        AND aesDecrypt(ic.title, :aesKey) != 'CKNB(내부)'
                        GROUP BY monthDate
                        ORDER BY monthDate asc
    """ , nativeQuery = true)
    List<CategoryScanInterface> getCategoryScan(@Param("aesKey")String aesKey, @Param("startMonth")String startMonth, @Param("endMonth")String endMonth);
}
