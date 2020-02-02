package com.podo.pododev.web.global.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class AuthFilter implements Filter {

    private final SecurityStore securityStore;

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;

        //extract token from header
        String accessToken = httpRequest.getHeader("Authorization");

        if (null != accessToken) {
            accessToken = accessToken.replace("Bearer", "");
            accessToken = accessToken.trim();

            Authentication authentication = securityStore.isAuthUserByToken(accessToken, LocalDateTime.now());
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }

        chain.doFilter(request, response);
    }

}
