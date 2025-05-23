package com.cknb.htPlatform.scan.repository;

import com.cknb.htPlatform.entity.IqrDetDailyEntity;
import com.cknb.htPlatform.scan.dto.vo.CustomerTopByNationInterface;
import com.cknb.htPlatform.scan.dto.vo.NationTopInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NationTopRepository extends JpaRepository<IqrDetDailyEntity, Integer> {

    // top 10 국가
    @Query(value = """
            SELECT nation
             , B.normal_count AS normalCount
             , B.expire_count AS expireCount
             , B.total_count  AS totalCount
             , ROUND(B.normal_count / B.total_count * 100, 2) AS normalRatio
             , ROUND(B.expire_count / B.total_count * 100, 2) AS expireRatio
             , C.flag_img AS flagImg
              FROM
                 (		
                    SELECT nation
                         , IFNULL(SUM(normal_count), 0) AS normal_count
                         , IFNULL(SUM(expire_count), 0) AS expire_count
                         , IFNULL(SUM(normal_count), 0) + IFNULL(SUM(expire_count), 0) AS total_count
                      FROM
                         (
                         SELECT nation
                             , SUM(CASE WHEN iqr_condition = 0 THEN IFNULL(det_count, 0) ELSE 0 END) AS normal_count
                             , SUM(CASE WHEN iqr_condition IN (2, 4) THEN IFNULL(det_count, 0) ELSE 0 END) AS expire_count
                          FROM iqr_det_daily
                         WHERE iqr_condition IN (0, 2, 4)
                        GROUP BY nation
                         ) A
                     WHERE nation != '없음'
                  GROUP BY nation
                  ORDER BY total_count desc
                 ) B
             LEFT JOIN country_info_mapping C
                    ON C.country_ko = B.nation
                    limit :limit
            """, nativeQuery = true)
    List<NationTopInterface> getNationTop(@Param("limit")Integer limit);


    // 국가별 스캔업체 top 10 쿼리
    @Query(value = """
            SELECT B.customer_cd AS customerCd
                 , B.name AS customer
                 , B.normal_count AS normalCount
                 , B.expire_count AS expireCount
                 , B.total_count  AS totalCount
                 , ROUND(B.normal_count / B.total_count * 100, 2) AS normalRatio
                 , ROUND(B.expire_count / B.total_count * 100, 2) AS expireRatio
                 , CASE
                     WHEN C.logo_img IS NOT NULL AND C.logo_img != '' THEN C.logo_img
                     ELSE 'https://d19cvjpkp3cfnf.cloudfront.net/appservice/img/flag_none.png'
                   END AS logoImg
              FROM
                 (
                    SELECT A.customer_cd
                         , IFNULL(NULLIF(aesDecrypt(display_brand_ko, :aesKey), ''), aesDecrypt(display_name_ko, :aesKey)) AS name
                         , IFNULL(SUM(normal_count), 0) AS normal_count
                         , IFNULL(SUM(expire_count), 0) AS expire_count
                         , IFNULL(SUM(normal_count), 0) + IFNULL(SUM(expire_count), 0) AS total_count
                      FROM
                         (
                            SELECT customer_cd
                                 , SUM(CASE WHEN iqr_condition = 0 THEN IFNULL(det_count, 0) ELSE 0 END) AS normal_count
                                 , SUM(CASE WHEN iqr_condition IN (2, 4) THEN IFNULL(det_count, 0) ELSE 0 END) AS expire_count
                              FROM iqr_det_daily
                             WHERE iqr_condition IN (0, 2, 4)
                              AND (:addressN IS NULL OR :addressN = '' OR nation = :addressN)
                              AND (:startDate IS NULL OR :startDate = '' OR :endDate IS NULL OR :endDate = '' OR det_date BETWEEN :startDate AND :endDate)
                            GROUP BY customer_cd
                         ) A
                 LEFT JOIN brand_master B
                        ON B.customer_cd = A.customer_cd
                     WHERE A.customer_cd >= 21
                       AND A.customer_cd NOT IN (74, 78, 87)
                  GROUP BY A.customer_cd
                  ORDER BY total_count DESC
                 ) B
          LEFT JOIN
                 (
                    SELECT customer_cd
                         , logo_img
                      FROM brand_rank
                     WHERE rank_index = (SELECT MAX(rank_index) FROM brand_rank)
                       AND lang = 'ko'
                 ) C
                ON C.customer_cd = B.customer_cd
          ORDER BY totalCount DESC
             LIMIT :limit
          """, nativeQuery = true)
    List<CustomerTopByNationInterface> getCustomerTopByNation(@Param("aesKey")String aesKey, @Param("addressN")String addressN,@Param("startDate")String startDate,@Param("endDate")String endDate, @Param("limit")Integer limit);



}
