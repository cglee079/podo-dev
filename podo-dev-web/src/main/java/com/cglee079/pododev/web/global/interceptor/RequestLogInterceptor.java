package com.cglee079.pododev.web.global.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

@Slf4j
@Component
public class RequestLogInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        Enumeration<String> dd = request.getHeaderNames();
//        while(dd.hasMoreElements()){
//            String header = dd.nextElement();
//            log.info("'{}' : '{}'", header, request.getHeader(header));
//        }


        String userAgent = request.getHeader("User-Agent");
        String ip = request.getRemoteAddr();
        String url = request.getRequestURI();

        log.info("'{}'  << '{}',  '{}'", url, ip, userAgent);

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception arg3) throws Exception {
    }
}