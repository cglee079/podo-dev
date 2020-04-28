package com.podo.pododev.web.global.config.interceptor;

import com.podo.pododev.core.util.type.RequestHeader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Slf4j
@Component
public class RequestLogInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String requestUserAgent = request.getHeader(RequestHeader.USER_AGENT.value());
        String requestIP = request.getHeader(RequestHeader.X_REAL_IP.value());

        if (Objects.isNull(requestIP)) {
            requestIP = request.getRemoteAddr();
        }

        final String requestUrl = request.getRequestURI();

        log.debug("요청 확인, '{}'  << '{}',  '{}'", requestUrl, requestIP, requestUserAgent);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception arg3) throws Exception {
    }
}
