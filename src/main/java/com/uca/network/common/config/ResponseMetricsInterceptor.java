package com.uca.network.common.config;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;


public class ResponseMetricsInterceptor extends HandlerInterceptorAdapter {
    private static final String TRACING_ID = "tracing_id";
    private static final String RESPONSE_TIME = "response_time";
    private static final Logger logger = LoggerFactory.getLogger(ResponseMetricsInterceptor.class);


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        String tracingId;
        //this judgement used for webs context
        if (StringUtils.isBlank(request.getHeader(TRACING_ID))) {
            tracingId = RandomStringUtils.randomAlphanumeric(30);
        } else {
            tracingId = request.getHeader(TRACING_ID);
        }
        response.setHeader(TRACING_ID, tracingId);
        //add a start time in request attribute
        request.setAttribute(RESPONSE_TIME, System.currentTimeMillis());
        MDC.put(TRACING_ID, tracingId);
        logger.info("tracing.begin.url={}", request.getServletPath());
        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        try {
            Object attribute = request.getAttribute(RESPONSE_TIME);
            Long timeStart = Long.valueOf(Objects.toString(attribute, "0"));
            long responseTime = System.currentTimeMillis() - timeStart;
            logger.info("tracing.end.url={}, 消耗时间:{}ms", request.getServletPath(), responseTime);
        } catch (Exception ignored) {

        } finally {
            //prevent memory leak
            MDC.remove(TRACING_ID);
            MDC.clear();
        }
        super.afterCompletion(request, response, handler, ex);
    }
}
