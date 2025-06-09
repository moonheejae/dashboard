package com.cknb.htPlatform.status.repository;

import com.cknb.htPlatform.entity.AppExecuteDeviceEntity;
import com.cknb.htPlatform.status.dto.vo.ReVisitInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReVisitRepository extends JpaRepository<AppExecuteDeviceEntity, Long> {
    @Query(value = """
             SELECT COUNT(DISTINCT aed.device_imei) AS revisitCount
            			FROM app_exe_device aed
            			JOIN app_exe_info aei ON aed.app_exe_no = aei.no
            			WHERE aei.exe_date >= CURDATE() - INTERVAL (:selectedDays - 1) day  
            			  AND EXISTS (
            			    SELECT 1
            			    FROM app_exe_device aed2
            			    JOIN app_exe_info aei2 ON aed2.app_exe_no = aei2.no
            			    WHERE aed2.device_imei = aed.device_imei
            			      AND aei2.exe_date BETWEEN CURDATE() - INTERVAL 90 DAY AND CURDATE() - INTERVAL :selectedDays day 
            			  )
            """, nativeQuery = true)
    ReVisitInterface getReVisitUser(@Param("selectedDays") Integer selectedDays);

}
