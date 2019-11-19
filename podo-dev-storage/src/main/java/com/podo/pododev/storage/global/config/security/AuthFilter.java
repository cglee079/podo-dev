package com.podo.pododev.storage.global.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class AuthFilter implements Filter {

    private final List<String> validTokens;

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;

        //extract token from header
        String accessToken = httpRequest.getHeader("Authorization");

        if (!Objects.isNull(accessToken)) {
            accessToken = accessToken.replace("Bearer", "");
            accessToken = accessToken.trim();

            if (validTokens.contains(accessToken)) {
                ClientAuth clientAuth = new ClientAuth(accessToken);
                clientAuth.addRole("ROLE_" + UserRole.ADMIN);

                SecurityContextHolder.getContext().setAuthentication(clientAuth);
            }
        }

        chain.doFilter(request, response);
    }

}
