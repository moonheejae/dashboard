package com.cknb.htPlatform.traffic.repository;

import com.cknb.htPlatform.entity.CommReviewEntity;
import com.cknb.htPlatform.traffic.dto.vo.CommunityInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommReviewRepository extends JpaRepository<CommReviewEntity, Long> {

    @Query(value = """
           SELECT
                    (SELECT COUNT(*)
                     FROM comm_review
                     WHERE (:selectedDays IS NULL OR
                            create_date >= CURDATE() - INTERVAL (:selectedDays - 1) DAY)) as postCnt,
                           
                    (SELECT SUM(pageviews_cnt)
                     FROM comm_review
                     WHERE (:selectedDays IS NULL OR
                            create_date >= CURDATE() - INTERVAL (:selectedDays - 1) DAY)) as viewCnt,
                           
                    (SELECT COUNT(*)
                     FROM comm_review_comment crc
                     INNER JOIN comm_review cr ON crc.review_no = cr.no
                     WHERE (:selectedDays IS NULL OR
                            crc.create_date >= CURDATE() - INTERVAL (:selectedDays - 1) DAY)) as commentCnt,
                           
                    (SELECT COUNT(*)
                     FROM comm_review_like crl
                     INNER JOIN comm_review cr ON crl.review_no = cr.no
                     WHERE (:selectedDays IS NULL OR
                            crl.create_date >= CURDATE() - INTERVAL (:selectedDays - 1) DAY)) as likeCnt,
           
                    (SELECT
                        SUM(CASE WHEN board_type = 1 THEN 1 ELSE 0 END)
                        FROM bookmark
                        where (:selectedDays IS NULL OR
                               create_date >= CURDATE() - INTERVAL (:selectedDays - 1) DAY)) as bookmarkCnt
            """, nativeQuery = true)
    CommunityInterface getCommReviewCount(@Param("selectedDays") Integer selectedDays);


}
