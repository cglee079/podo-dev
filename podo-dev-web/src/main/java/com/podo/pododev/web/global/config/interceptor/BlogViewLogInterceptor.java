package com.podo.pododev.web.global.config.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Slf4j
@Component
public class BlogViewLogInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String requestUserAgent = request.getHeader("User-Agent");

        String requestIP = request.getHeader("x-real-ip");

        if (Objects.isNull(requestIP)) {
            requestIP = request.getRemoteAddr();
        }

        final String requestUrl = request.getRequestURI();

        log.info("블로그 조회, '{}'  << '{}',  '{}'", requestUrl, requestIP, requestUserAgent);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception arg3) throws Exception {
    }
}
