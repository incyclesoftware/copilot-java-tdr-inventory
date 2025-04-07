package com.inventory.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/saveOrderServlet")
public class SaveOrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String DB_URL = "jdbc:sqlite:database/inventory.db";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userId = request.getParameter("userId");
        String[] productIds = request.getParameterValues("productId[]");
        String[] quantities = request.getParameterValues("quantity[]");
        String[] prices = request.getParameterValues("pricePerUnit[]");

        // Validate input
        if (userId == null || productIds == null || quantities == null || prices == null ||
                productIds.length != quantities.length || quantities.length != prices.length) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid order data");
            return;
        }

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new ServletException("SQLite JDBC driver not found", e);
        }

        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            conn.setAutoCommit(false);
            PreparedStatement orderStmt = conn.prepareStatement(
                    "INSERT INTO orders (user_id, order_date) VALUES (?, datetime('now'))",
                    Statement.RETURN_GENERATED_KEYS);
            PreparedStatement itemStmt = conn.prepareStatement(
                    "INSERT INTO order_items (order_id, product_id, quantity, price_per_unit) VALUES (?, ?, ?, ?)");

            orderStmt.setInt(1, Integer.parseInt(userId));
            orderStmt.executeUpdate();

            int orderId = orderStmt.getGeneratedKeys().getInt(1);

            for (int i = 0; i < productIds.length; i++) {
                itemStmt.setInt(1, orderId);
                itemStmt.setInt(2, Integer.parseInt(productIds[i]));
                itemStmt.setInt(3, Integer.parseInt(quantities[i]));
                itemStmt.setBigDecimal(4, new BigDecimal(prices[i]));
                itemStmt.addBatch();
            }

            itemStmt.executeBatch();
            conn.commit();
            response.sendRedirect("orderSuccess.jsp");
        } catch (Exception e) {
            throw new ServletException("Database error", e);
        }
    }
}
