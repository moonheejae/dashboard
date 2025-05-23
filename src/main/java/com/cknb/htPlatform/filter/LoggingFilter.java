package com.cknb.htPlatform.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoggingFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String ipAddress = httpRequest.getRemoteAddr(); // 클라이언트 IP
        String uri = httpRequest.getRequestURI();      // 요청 URI
        String method = httpRequest.getMethod();       // HTTP 메서드 (GET, POST 등)
        // 접속 정보를 DB에 저장하거나 로그로 기록
        LOGGER.info("=====[Filter]===== Request received from IP: {}, URI: {}, Method: {}", ipAddress, uri, method);
        // 필터 체인을 이어서 처리
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
