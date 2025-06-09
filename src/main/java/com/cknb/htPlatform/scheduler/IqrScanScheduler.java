package com.cknb.htPlatform.scheduler;

import com.cknb.htPlatform.Enum.RedisChannel;
import com.cknb.htPlatform.scheduler.dto.ResponseScanDto;
import com.cknb.htPlatform.websocket.handler.WebSocketHandler;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static io.netty.handler.codec.HeadersUtils.getAsString;
import static org.apache.commons.lang3.function.Failable.getAsLong;

@EnableScheduling
@Component
@Slf4j
@RequiredArgsConstructor
public class IqrScanScheduler {
    @Value("${aes.key}")
    private String AESKEY;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private LocalDateTime lastPolledTime = LocalDateTime.now();
    // 웹소켓
    private final RedisMessageListenerContainer listenerContainer;
    private final WebSocketHandler webSocketHandler;

    @Scheduled(fixedRate = 1000)
    public void pollForNewData() {
        try {
            LocalDateTime currentTime = LocalDateTime.now();

            String sql = """
                    SELECT 
                        iqrDet.idx
                         , iqrDet.det_time
                         , iqrDet.address_n
                         , iqrDet.device_imei
                         , iqrDet.iqr_condition
                         , iqrDet.app_gubun
                         , aesDecrypt(iqrCustomer.title, ?) as customer_name
                         , aesDecrypt(brandMaster.display_brand_ko, ?) as brand_name
                    FROM iqr_det_info iqrDet
                        LEFT JOIN
                            iqr_service_config iqrService ON iqrDet.service_no = iqrService.no
                        LEFT JOIN
                            iqr_customer iqrCustomer ON iqrCustomer.no = iqrService.customer_cd
                        LEFT JOIN
                            brand_master brandMaster ON iqrCustomer.no = brandMaster.customer_cd
                    WHERE idx != 80 AND det_time > ? AND det_time <= ?
                    ORDER BY det_time DESC limit 10
                """;

            List<Map<String, Object>> newRows = jdbcTemplate.queryForList(
                    sql,
                    AESKEY,
                    AESKEY,
                    Timestamp.valueOf(lastPolledTime),
                    Timestamp.valueOf(currentTime)
            );

            if (!newRows.isEmpty()) {
                log.info("새로운 데이터 {} 건 감지", newRows.size());
                processNewDataBatch(newRows);
            }

            lastPolledTime = currentTime;

        } catch (Exception e) {
            log.error("타임스탬프 기반 폴링 중 오류 발생", e);
        }
    }

    private void processNewDataBatch(List<Map<String, Object>> rows) {
        for (Map<String, Object> row : rows) {
            // 비즈니스 로직 처리
            handleDataUpdate(row);
        }
    }

    private void handleDataUpdate(Map<String, Object> row) {
        String idx = getAsString(row, "idx");
        String detTime = getAsString(row, "det_time");
        String deviceImei = getAsString(row, "device_imei");
        String iqrCondition = getAsString(row, "iqr_condition");
        String addressN = getAsString(row, "address_n");
        String appGubun = getAsString(row, "app_gubun");
        String brandName = getAsString(row, "brand_name");
        String customerName = getAsString(row, "customer_name");
        webSocketHandler.broadcast(RedisChannel.SCAN_MONITOR.getChannelName(), ResponseScanDto.builder()
                .idx(idx)
                .addressN(addressN)
                .deviceImei(deviceImei)
                .detTime(detTime)
                .iqrCondition(iqrCondition)
                .customerName(customerName)
                .brandName(brandName)
                .appGubun(appGubun)
                .build());
        log.info("데이터 처리: ID={}, 스캔 시간={}, 국가={}, 기기={}, 상태={}, 업체={}, 브랜드={}, 앱={}",
                row.get("idx"), row.get("det_time"), row.get("address_n"), row.get("device_imei") ,row.get("iqr_condition"),row.get("customer_name") ,row.get("brand_name"),row.get("app_gubun"));
    }

    // 안전한 타입 변환 유틸리티 메서드들
    private String getAsString(Map<String, Object> row, String key) {
        Object value = row.get(key);
        if (value == null) {
            return null;
        }
        return value.toString();
    }
}
