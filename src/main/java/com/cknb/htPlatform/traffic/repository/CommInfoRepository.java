package com.cknb.htPlatform.traffic.repository;

import com.cknb.htPlatform.entity.CommInfoEntity;
import com.cknb.htPlatform.traffic.dto.vo.CommunityInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommInfoRepository extends JpaRepository<CommInfoEntity, Long> {
    @Query(value = """
            SELECT
                    (SELECT COUNT(*)
                     FROM comm_info
                     WHERE (:selectedDays IS NULL OR
                            create_date >= CURDATE() - INTERVAL (:selectedDays - 1) DAY)) as postCnt,
                          
                    (SELECT SUM(pageviews_cnt)
                     FROM comm_info
                     WHERE (:selectedDays IS NULL OR
                            create_date >= CURDATE() - INTERVAL (:selectedDays - 1) DAY)) as viewCnt,
                          
                    (SELECT COUNT(*)
                     FROM comm_info_comment cic
                     INNER JOIN comm_info ci ON cic.info_no = ci.no
                     WHERE (:selectedDays IS NULL OR
                            cic.create_date >= CURDATE() - INTERVAL (:selectedDays - 1) DAY)) as commentCnt,
                          
                    (SELECT COUNT(*)
                     FROM comm_info_like cil
                     INNER JOIN comm_info ci ON cil.info_no = ci.no
                     WHERE (:selectedDays IS NULL OR
                            cil.create_date >= CURDATE() - INTERVAL (:selectedDays - 1) DAY)) as likeCnt,
                     
                     (SELECT
					    SUM(CASE WHEN board_type = 3 THEN 1 ELSE 0 END)
						FROM bookmark
						where (:selectedDays IS NULL OR
						       create_date >= CURDATE() - INTERVAL (:selectedDays - 1) DAY)) as bookmarkCnt
            """, nativeQuery = true)
    CommunityInterface getCommInfoCount(@Param("selectedDays") Integer selectedDays);
}
