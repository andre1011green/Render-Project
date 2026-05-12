package com.Practice.Render.SecurityConfiguration;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler
{
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException
    {
        // Check if the user is a manager
        if (request.isUserInRole("MANAGER"))
        {
            System.out.println("Oh Man You're a manager");
            response.sendRedirect("/error/manager-access-denied");
        }
        else
        {
            System.out.println("Not a manager OR employee");
            response.sendRedirect("/error/access-denied");
        }
    }
}
