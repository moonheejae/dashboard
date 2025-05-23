package com.cknb.htPlatform.scheduler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableScheduling
@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/scheduler")
public class CollectScheduler {

    /*@Scheduled(cron = "", zone = "Asia/Seoul")
    @GetMapping
    public void runSchedule() {
        // scheduler
        System.out.println("scheduler");
    }*/
}
