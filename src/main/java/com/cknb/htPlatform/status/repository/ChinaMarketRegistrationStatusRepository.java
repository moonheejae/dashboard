package com.cknb.htPlatform.status.repository;

import com.cknb.htPlatform.entity.ChinaMarketRegistrationStatusEntity;
import com.cknb.htPlatform.status.dto.vo.ChinaMarketRegistrationRateInterface;
import com.cknb.htPlatform.status.dto.vo.ChinaMarketStatusInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChinaMarketRegistrationStatusRepository extends JpaRepository<ChinaMarketRegistrationStatusEntity, Long> {
    @Query(value = """
            SELECT
                cm.china_market_name as chinaMarket,
                cm.hiddentag as hidden,
                cm.hiddentag_cop as cop,
                cm.hiddentag_global as global,
                cm.last_upload_date as lastUpdatedDate,
                cm.status as status,
                cm.create_date as createDate
            FROM
                china_market_registration_status cm
            INNER JOIN (
                SELECT
                    app_kind,
                    market_no,
                    MAX(update_date) AS max_update_date
                FROM
                    china_market_registration_status
                GROUP BY
                    app_kind,
                    market_no
            ) AS latest
            ON cm.app_kind = latest.app_kind
            AND cm.market_no = latest.market_no
            AND cm.update_date = latest.max_update_date
            Order by cm.last_upload_date desc;
            """, nativeQuery = true)
    List<ChinaMarketStatusInterface> getChinaMarketRegistrationStatus();


    @Query(value = """
        SELECT
            status,
            COUNT(*) as count,
            FLOOR(COUNT(*) * 100.0 / SUM(COUNT(*)) OVER() * 10) / 10 as registrationRate
        FROM (
            SELECT cm.status
            FROM china_market_registration_status cm
            INNER JOIN (
                SELECT
                    app_kind,
                    market_no,
                    MAX(update_date) AS max_update_date
                FROM
                    china_market_registration_status
                GROUP BY
                    app_kind,
                    market_no
            ) AS latest
            ON cm.app_kind = latest.app_kind
            AND cm.market_no = latest.market_no
            AND cm.update_date = latest.max_update_date
        ) status_data
        GROUP BY status
        ORDER BY count DESC;
""", nativeQuery = true)
    List<ChinaMarketRegistrationRateInterface> getChinaMarketRegistrationRate();
}
