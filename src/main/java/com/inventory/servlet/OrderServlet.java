package com.inventory.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.inventory.model.Order;

public class OrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("user") == null) {
            resp.sendRedirect("index.jsp");
            return;
        }
        String id = req.getParameter("id");
        if (id != null) {
            try {
                Class.forName("org.sqlite.JDBC");
                try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database/inventory.db")) {
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM orders WHERE id=" + id);
                    if (rs.next()) {
                        Order order = new Order();
                        order.id = rs.getInt("id");
                        order.userId = rs.getInt("user_id");
                        order.orderDateTime = java.sql.Timestamp.valueOf(rs.getString("order_date"));
                        req.setAttribute("order", order);
                    } else {
                        req.setAttribute("order", null);
                    }
                    req.getRequestDispatcher("editOrder.jsp").forward(req, resp);
                }
            } catch (IOException | ClassNotFoundException | SQLException | ServletException e) {
                resp.getWriter().write(e.getMessage());
            }
        } else {
            try {
                Class.forName("org.sqlite.JDBC");
                try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database/inventory.db")) {
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM orders");
                    
                    List<Order> orders = new ArrayList<>();
                    while (rs.next()) {
                        Order order = new Order();
                        order.id = rs.getInt("id");
                        order.userId = rs.getInt("user_id");
                        order.orderDateTime = rs.getTimestamp("order_date");
                        orders.add(order);
                    }
                    
                    req.setAttribute("orders", orders);
                    req.getRequestDispatcher("orders.jsp").forward(req, resp);
                }
            } catch (IOException | ClassNotFoundException | SQLException | ServletException e) {
                resp.getWriter().write(e.getMessage());
            }
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("user") == null) {
            resp.sendRedirect("index.jsp");
            return;
        }
        String id = req.getParameter("id");
        String userId = req.getParameter("user_id");
        String orderDateTime = req.getParameter("order_date");
        String status = req.getParameter("status");

        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:database/inventory.db");
                Statement stmt = conn.createStatement();
                if (id == null || id.isEmpty()) {
                    stmt.executeUpdate("INSERT INTO orders (user_id, order_date, status) VALUES ('" + userId + "', '" + orderDateTime + "', '" + status + "')");
                } else {
                    stmt.executeUpdate("UPDATE orders SET user_id='" + userId + "', order_date='" + orderDateTime + "', status='" + status + "' WHERE id=" + id);
                }            
            resp.sendRedirect("orders");
        } catch (Exception e) {
            resp.getWriter().write(e.getMessage());
        }
    }
}