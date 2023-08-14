package com.jinnyshop5.authencation.config;

import com.jinnyshop5.authencation.config.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;

    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String HEADER_COOKIE = "Cookie";
    private final static String TOKEN_PREFIX = "Bearer ";
    private final static String COOKIE_TOKEN_PREFIX = "access_token=";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)  throws ServletException, IOException {

        String token ="";
        String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);
        String cookieHeader = request.getHeader(HEADER_COOKIE);

        if (cookieHeader != null) {
            token = getTokenForHeader(cookieHeader,COOKIE_TOKEN_PREFIX);
        }
        if (authorizationHeader != null) {
            token = getTokenForHeader(authorizationHeader,TOKEN_PREFIX);
        }
        if (tokenProvider.validToken(token)) {
            Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenForHeader(String header, String prefix) {
        if (StringUtils.hasText(header)) {
            return header.substring(header.indexOf(prefix) + prefix.length());
        }

        return null;
    }
}

