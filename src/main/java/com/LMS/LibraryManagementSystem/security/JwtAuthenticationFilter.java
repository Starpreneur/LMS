package com.LMS.LibraryManagementSystem.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;


public class JwtAuthenticationFilter implements Filter {

    private final JwtService jwtService = new JwtService();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

       HttpServletRequest request = (HttpServletRequest)servletRequest;
       HttpServletResponse response = (HttpServletResponse)servletResponse;

        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            for (Cookie cookie : cookies){
                if (("authToken").equals(cookie.getName())){
                    String token = cookie.getValue();
                    try{
                        Claims claims = jwtService.validateToken(token);
                        request.setAttribute("userName",claims.get("userName"));
                        request.setAttribute("userRole",claims.get("userRole"));
                        request.setAttribute("emailId",claims.get("emailId"));
                        break;
                    }catch (Exception e){
                        response.sendRedirect("/api/login/showLogin");
                        System.out.println(e.getMessage());
                        return;
                    }
                }
            }
        }else{
            response.sendRedirect("/api/login/showLogin");
            return;
        }

        filterChain.doFilter(request,response);
    }


}
