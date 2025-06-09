package com.cknb.htPlatform.traffic.repository;

import com.cknb.htPlatform.entity.CommDebateEntity;
import com.cknb.htPlatform.traffic.dto.vo.CommunityInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommDebateRepository extends JpaRepository<CommDebateEntity, Long> {

    @Query(value = """
            SELECT
                    (SELECT COUNT(*)
                     FROM comm_debate
                     WHERE (:selectedDays IS NULL OR
                            create_date >= CURDATE() - INTERVAL (:selectedDays - 1) DAY)) as postCnt,
                          
                    (SELECT SUM(pageviews_cnt)
                     FROM comm_debate
                     WHERE (:selectedDays IS NULL OR
                            create_date >= CURDATE() - INTERVAL (:selectedDays - 1) DAY)) as viewCnt,
                          
                    (SELECT COUNT(*)
                     FROM comm_debate_comment cdc
                     INNER JOIN comm_debate cd ON cdc.debate_no = cd.no
                     WHERE (:selectedDays IS NULL OR
                            cdc.create_date >= CURDATE() - INTERVAL (:selectedDays - 1) DAY)) as commentCnt,
                          
                    (SELECT COUNT(*)
                     FROM comm_debate_like cdl
                     INNER JOIN comm_debate cd ON cdl.debate_no = cd.no
                     WHERE (:selectedDays IS NULL OR
                            cdl.create_date >= CURDATE() - INTERVAL (:selectedDays - 1) DAY)) as likeCnt,
                     
                     (SELECT
					    SUM(CASE WHEN board_type = 2 THEN 1 ELSE 0 END)
						FROM bookmark
						where (:selectedDays IS NULL OR
						       create_date >= CURDATE() - INTERVAL (:selectedDays - 1) DAY)) as bookmarkCnt
            """, nativeQuery = true)
    CommunityInterface getCommDebateCount(@Param("selectedDays") Integer selectedDays);
}
