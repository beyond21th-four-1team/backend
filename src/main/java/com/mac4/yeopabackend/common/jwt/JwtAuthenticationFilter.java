package com.mac4.yeopabackend.common.jwt;

import com.mac4.yeopabackend.common.exception.BusinessException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    private final TokenBlacklistStore tokenBlacklistStore;


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String token = resolveToken(request);

        if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            String email;
            UserDetails userDetails;
            try {
                // 1) 토큰 검증 (문제 있으면 예외 -> EntryPoint에서 401 처리)
                jwtTokenProvider.validateToken(token);
                if (tokenBlacklistStore.isBlacklisted(token)) {
                    throw new BadCredentialsException("JWT is blacklisted"); // 인증 실패
                }
                // 2) 토큰에서 email 추출
                email = jwtTokenProvider.getUsernameFromJWT(token);
                // 3) email로 UserDetails 로드 (CustomUserDetailsService가 처리)
                userDetails = userDetailsService.loadUserByUsername(email);

            } catch (BusinessException | io.jsonwebtoken.JwtException e) {
                // AuthenticationException으로 감싸서 EntryPoint로 보내기
                throw new BadCredentialsException("JWT authentication failed", e);
            } catch (Exception e) {
                // 예상치 못한 예외도 인증 실패로 처리
                throw new BadCredentialsException("JWT authentication failed", e);
            }

            // 4) 인증 객체 생성 후 SecurityContext에 등록
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (bearer == null) return null;

        if (bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}
