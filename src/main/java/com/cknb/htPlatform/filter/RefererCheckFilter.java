package com.cknb.htPlatform.filter;

import com.cknb.htPlatform.auth.service.UserService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class RefererCheckFilter implements Filter {
    // 레퍼럴 체크 활성화 여부 설정
    @Value("${application.security.referer-check-enabled}")
    private boolean refererCheckEnabled;
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingFilter.class);
    @Autowired
    private UserService userService;

    @Value("${application.urls.admin}")
    private String ALLOWED_REFERER;
    @Value("${application.urls.wAdmin}")
    private String ALLOWED_REFERER_W;
    @Value("${application.urls.data_report}")
    private String DATA_REPORT_URL;

    private static final String REFERER_CHECKED = "refererChecked"; // 세션 저장 키


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        Boolean refererChecked = (Boolean) httpRequest.getSession().getAttribute(REFERER_CHECKED);
        LOGGER.info("=====[1. RefererCheckFilter]===== Request received from refererCheckEnabled: {}, URI: {}, refererChecked: {}", refererCheckEnabled, httpRequest.getRequestURI(), refererChecked);
        // 레퍼럴 체크가 비활성화되어 있으면 필터를 건너뜀
        if (!refererCheckEnabled) {
            chain.doFilter(request, response);
            LOGGER.info("=====[비활성화]===== 통과됩니다.");
            return;
        }
        if (httpRequest.getRequestURI().contains("/")) {
            if( refererChecked == null || !refererChecked ) {
                String referer = httpRequest.getHeader("Referer");
                String userNo = httpRequest.getHeader("UserNo");

                LOGGER.info("=====[2. 레퍼럴 체크]===== referer : {}, userNo : {}", referer, userNo);
                // 레퍼럴 체크
                if (referer == null || (!referer.startsWith(ALLOWED_REFERER) && !referer.startsWith(ALLOWED_REFERER_W))) {
                    String redirectUrl = ALLOWED_REFERER;
                    httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    httpResponse.setContentType("application/json");
                    httpResponse.getWriter().write("{\"success\": false, \"redirectUrl\":\"" + redirectUrl + "\"}");
                    httpResponse.sendRedirect(ALLOWED_REFERER);
                    LOGGER.info("=====[3. 잘못된 경로로 접근]===== 허용된 URL : {}, 접속한 URL : {}", ALLOWED_REFERER, httpRequest.getRequestURL());
                    return;
                }

                // userNo가 있으면 토큰 생성
                if (userNo != null && !userNo.isEmpty()) {
                    // 리다이렉트 URL에 userNo와 token 포함
                    String redirectUrl = DATA_REPORT_URL + "isValid?userNo=" + userNo;
                    LOGGER.info("=====[3. 레퍼럴 통과]===== userNo : {}, redirectUrl : {}", userNo, redirectUrl);
                    // JSON 응답
                    httpResponse.setContentType("application/json");
                    httpResponse.setCharacterEncoding("UTF-8");
                    httpResponse.getWriter().write("{\"success\": true, \"redirectUrl\":\"" + redirectUrl + "\"}");
                }
                httpRequest.getSession().setAttribute(REFERER_CHECKED, true);
            }
        }
        LOGGER.info("=====[4. 레퍼럴 체크 완료]===== refererChecked : {}", refererChecked);
        // 필터 체인 계속 진행
        chain.doFilter(request, response);
    }
}
