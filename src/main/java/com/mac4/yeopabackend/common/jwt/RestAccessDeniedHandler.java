package com.mac4.yeopabackend.common.jwt;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {


    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        String timestamp = java.time.LocalDateTime.now().toString();

        String jsonResponse = "{"
                + "\"timestamp\":\"" + timestamp + "\","
                + "\"status\":403,"
                + "\"code\":\"A002\","
                + "\"message\":\"접근 권한이 없습니다.\""
                + "}";

        response.getWriter().write(jsonResponse);

    }
}
