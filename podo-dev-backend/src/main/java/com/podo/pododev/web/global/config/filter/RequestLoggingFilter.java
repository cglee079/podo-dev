package com.podo.pododev.web.global.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.podo.pododev.core.util.DateTimeFormatUtil;
import net.logstash.logback.argument.StructuredArguments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestLoggingFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger("ACCESS_LOGGER");
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        ContentCachingRequestWrapper wrappingRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappingResponse = new ContentCachingResponseWrapper(response);

        ThreadLocalContext.init("request");
        try {
            filterChain.doFilter(wrappingRequest, wrappingResponse);
        } catch (Exception ex) {
            ThreadLocalContext.putException(ex);
        } finally {
            ThreadLocalContext.put("request", extractRequestLog(wrappingRequest, LocalDateTime.now()));
            ThreadLocalContext.put("response", extractResponseLog(wrappingResponse));

            LOGGER.info("", StructuredArguments.value("context", ThreadLocalContext.toLog()));

            wrappingResponse.copyBodyToResponse();
            ThreadLocalContext.removeAll();
        }
    }

    private Map<String, Object> extractRequestLog(ContentCachingRequestWrapper request, LocalDateTime now) throws IOException {
        HashMap<String, Object> log = new HashMap<>();
        log.put("url", request.getRequestURI());
        log.put("queryString", request.getQueryString());
        log.put("method", request.getMethod());

        Map<String, List<String>> headers = Collections.list(request.getHeaderNames()).stream()
                .distinct()
                .collect(Collectors.toMap(h -> h, h -> new ArrayList<>()));

        for (String headerName : Collections.list(request.getHeaderNames())) {
            headers.get(headerName).add(request.getHeader(headerName));
        }

        log.put("userAgent", headers.get("user-agent"));
        log.put("origin", headers.get("origin"));
        log.put("x-real-ip", headers.get("x-real-ip"));
        log.put("x-forwarded-for", headers.get("x-forwarded-for"));
        log.put("remote-addr", request.getRemoteAddr());
        log.put("headers", OBJECT_MAPPER.writeValueAsString(headers));
        log.put("parameters", request.getParameterMap());
        log.put("body", OBJECT_MAPPER.readTree(request.getContentAsByteArray()).toString());
        log.put("bodySize", request.getContentLength());
        log.put("createAt", DateTimeFormatUtil.toFullDateTime(now));

        return log;
    }

    private Map<String, Object> extractResponseLog(ContentCachingResponseWrapper response) throws IOException {
        HashMap<String, Object> log = new HashMap<>();

        Map<String, List<String>> headers = response.getHeaderNames().stream()
                .distinct()
                .collect(Collectors.toMap(h -> h, h -> new ArrayList<>()));

        for (String headerName : response.getHeaderNames()) {
            headers.get(headerName).add(response.getHeader(headerName));
        }


        log.put("headers", OBJECT_MAPPER.writeValueAsString(headers));
        log.put("status", response.getStatus());
        log.put("body", OBJECT_MAPPER.readTree(response.getContentAsByteArray()).toString());
        log.put("bodySize", response.getContentSize());

        return log;
    }
}
