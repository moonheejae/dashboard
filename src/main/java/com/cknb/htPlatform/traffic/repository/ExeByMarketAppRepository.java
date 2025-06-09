package com.cknb.htPlatform.traffic.repository;

import com.cknb.htPlatform.entity.AppExecuteDeviceEntity;
import com.cknb.htPlatform.traffic.dto.vo.ExeByMarketAppInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExeByMarketAppRepository extends JpaRepository<AppExecuteDeviceEntity, Long> {
    @Query(value = """
                       		SELECT 
                       		        app_kind as appKind
                    				,count(CASE WHEN market = 0 THEN 1 END) AS appStore
                    				,count(CASE WHEN market = 1 THEN 1 END) AS playStore
                    				,count(CASE WHEN market = 2 THEN 1 END) AS oneStore
                    				,count(CASE WHEN market NOT IN (0,1,2,12,99) THEN 1 END) AS chinaStore
                    				,count(CASE WHEN market = 12 THEN 1 END) AS wechat
                    				,count(CASE WHEN market IS NULL THEN 1 END) AS non
                    				,count(CASE WHEN market = 99 THEN 1 END) AS un
                    		FROM app_exe_device aed
                    		LEFT JOIN app_exe_info aei ON aed.app_exe_no = aei.no
                    		WHERE aei.exe_date BETWEEN CURDATE() - INTERVAL (:selectedDays - 1) DAY AND CURDATE() group by app_kind;
            """, nativeQuery = true)
    List<ExeByMarketAppInterface> getExeByMarketApp(@Param("selectedDays") Integer selectedDays);

}
