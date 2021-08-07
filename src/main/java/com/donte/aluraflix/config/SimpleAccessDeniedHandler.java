package com.donte.aluraflix.config;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.donte.aluraflix.exception.CustomError;

public class SimpleAccessDeniedHandler implements AccessDeniedHandler {
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
		
		CustomError error = new CustomError("Você não tem permissão para acessar este recurso", HttpServletResponse.SC_UNAUTHORIZED);
	       
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
               
        PrintWriter printWriter = response.getWriter();
        printWriter.print(error.toJson());
        printWriter.flush();
        printWriter.close();        
	}
}