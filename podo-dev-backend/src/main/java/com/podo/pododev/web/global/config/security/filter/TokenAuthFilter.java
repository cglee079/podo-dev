package com.podo.pododev.web.global.config.security.filter;

import com.podo.pododev.core.util.type.RequestHeader;
import com.podo.pododev.web.global.config.security.SecurityStore;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

@RequiredArgsConstructor
public class TokenAuthFilter implements Filter {

    public static final String AUTH_HEADER_VALUE_PREFIX = "Bearer";

    private final SecurityStore securityStore;

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;

        String accessToken = httpRequest.getHeader(RequestHeader.AUTHORIZATION.name());

        if (Objects.nonNull(accessToken) ) {
            accessToken = accessToken.replace(AUTH_HEADER_VALUE_PREFIX, "");
            accessToken = accessToken.trim();

            Authentication authentication = securityStore.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }

}
