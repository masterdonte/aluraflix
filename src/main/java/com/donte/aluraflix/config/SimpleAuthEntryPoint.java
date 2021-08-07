package com.donte.aluraflix.config;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.donte.aluraflix.exception.CustomError;

public class SimpleAuthEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
    	CustomError error = new CustomError(authException.getMessage(), HttpServletResponse.SC_UNAUTHORIZED);
       
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
               
        PrintWriter printWriter = response.getWriter();
        printWriter.print(error.toJson());
        printWriter.flush();
        printWriter.close();
    }
}