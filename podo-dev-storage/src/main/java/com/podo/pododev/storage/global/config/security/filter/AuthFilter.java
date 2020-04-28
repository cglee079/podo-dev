package com.podo.pododev.storage.global.config.security.filter;

import com.podo.pododev.core.util.type.RequestHeader;
import com.podo.pododev.storage.global.config.security.ClientAuth;
import com.podo.pododev.storage.global.config.security.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class AuthFilter implements Filter {

    private static final String AUTH_HEADER_VALUE_PREFIX = "Bearer";

    private final List<String> validTokens;

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;

        final String accessToken = getAccessToken(httpRequest);

        if (validTokens.contains(accessToken)) {
            SecurityContextHolder.getContext().setAuthentication(createAuthByToken(accessToken));
        }

        chain.doFilter(request, response);
    }

    private String getAccessToken(HttpServletRequest httpRequest) {
        final String authorization = httpRequest.getHeader(RequestHeader.AUTHORIZATION.value());

        if (!Objects.isNull(authorization)) {
            return authorization.replace(AUTH_HEADER_VALUE_PREFIX, "").trim();
        }

        return null;
    }

    private ClientAuth createAuthByToken(String accessToken) {
        final ClientAuth clientAuth = new ClientAuth(accessToken);
        clientAuth.addRole("ROLE_" + UserRole.ADMIN);

        return clientAuth;
    }


}
