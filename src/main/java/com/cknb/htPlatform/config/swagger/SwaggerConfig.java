package com.cknb.htPlatform.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile({"local","dev","test","staging"})
public class SwaggerConfig {
    @Value("${spring.profiles.active}")
    private String activeProfile;
    @Value("${server.name}")
    private String serverName;

    @Value("${springdoc.server.url}")
    private String serverUrl;

    @Bean
    public OpenAPI openAPI() {

        Server server = new Server();
        server.setUrl(serverUrl);

        Info info = new Info()
                .title("API document - "+ serverName)
                .version(null)
                .license(new License().name("data-report").url("com"))
                .description("["+serverName+"] "+" API - "+ activeProfile);
        return new OpenAPI().components(new Components()).info(info).servers(List.of(server));
    }

}
