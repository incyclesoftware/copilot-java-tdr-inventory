package com.inventory.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if("admin".equals(username) && "password".equals(password)){
            request.getSession().setAttribute("user", username);
            response.sendRedirect("inventory");
        } else {
            response.sendRedirect("index.jsp?error=true");
        }
    }
}