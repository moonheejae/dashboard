package com.cknb.htPlatform;

import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@EnableScheduling
@SpringBootApplication
@OpenAPIDefinition(servers = {
         @Server(url="http://hiddentagiqr.com:8020/", description = "serverUrl")
})
public class HtDataReportApplication {

    public static void main(String[] args) {
        SpringApplication.run(HtDataReportApplication.class, args);
    }

}
