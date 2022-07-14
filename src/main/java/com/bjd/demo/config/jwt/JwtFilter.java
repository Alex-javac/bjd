package com.bjd.demo.config.jwt;

import static com.bjd.demo.util.UtilConst.AUTHORIZATION;
import static com.bjd.demo.util.UtilConst.BEARER;
import static org.springframework.util.StringUtils.hasText;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bjd.demo.config.CustomUserDetails;
import com.bjd.demo.config.CustomUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws
            IOException, ServletException, UsernameNotFoundException {
        log.info("do filter...");
        String token = getTokenFromRequest(request);
        if (token != null && jwtProvider.validateToken(token)) {
            String email = jwtProvider.getEmailFromToken(token);
            CustomUserDetails customUserDetails = customUserDetailsService.loadUserByUsername(email);
            UsernamePasswordAuthenticationToken
                    auth = new UsernamePasswordAuthenticationToken(customUserDetails, null,
                    customUserDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearer = String.valueOf(request.getSession().getAttribute(AUTHORIZATION));
        if (hasText(bearer) && bearer.startsWith(BEARER)) {
            return bearer.substring(BEARER.length());
        }
        return null;
    }
}