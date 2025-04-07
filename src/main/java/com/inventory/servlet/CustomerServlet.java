package com.inventory.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.inventory.model.User;

public class CustomerServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("user") == null) {
            resp.sendRedirect("index.jsp");
            return;
        }
        String id = req.getParameter("id");
        if (id != null) {
            try {
                Class.forName("org.sqlite.JDBC");
                Connection conn = DriverManager.getConnection("jdbc:sqlite:database/inventory.db");
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE id=" + id + " and user_role='CUSTOMER'");
                if (rs.next()) {
                    User user = new User();
                    user.id = rs.getInt("id");
                    user.firstName = rs.getString("first_name");
                    user.lastName = rs.getString("last_name");
                    user.email = rs.getString("email");
                    user.phone = rs.getString("phone");
                    req.setAttribute("customer", user);
                } else {
                    req.setAttribute("customer", null);
                }
                req.getRequestDispatcher("editCustomer.jsp").forward(req, resp);

            } catch (Exception e) {
                resp.getWriter().write(e.getMessage());
            }
        } else {
            try {
                Class.forName("org.sqlite.JDBC");
                Connection conn = DriverManager.getConnection("jdbc:sqlite:database/inventory.db");
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE user_role='CUSTOMER'");
                List<User> customers = new ArrayList<>();

                while (rs.next()) {
                    User user = new User();
                    user.id = rs.getInt("id");
                    user.firstName = rs.getString("first_name");
                    user.lastName = rs.getString("last_name");
                    user.email = rs.getString("email");
                    user.phone = rs.getString("phone");
                    customers.add(user);
                }

                req.setAttribute("customers", customers);
                req.getRequestDispatcher("customers.jsp").forward(req, resp);
            } catch (Exception e) {
                resp.getWriter().write(e.getMessage());
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("user") == null) {
            resp.sendRedirect("index.jsp");
            return;
        }
        String id = req.getParameter("id");
        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");

        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:database/inventory.db");
            Statement stmt = conn.createStatement();
            if (id == null || id.isEmpty()) {
                stmt.executeUpdate("INSERT INTO users (first_name, last_name, email, phone, user_role) VALUES ('"
                        + firstName + "', '" + lastName + "', '" + email + "', '" + phone + "', 'CUSTOMER')");
            } else {
                stmt.executeUpdate("UPDATE users SET first_name='" + firstName + "', last_name='" + lastName
                        + "', email='" + email + "', phone='" + phone + "' WHERE id=" + id);
            }
            resp.sendRedirect("customers");
        } catch (Exception e) {
            resp.getWriter().write(e.getMessage());
        }
    }
}
