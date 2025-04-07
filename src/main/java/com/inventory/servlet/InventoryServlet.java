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

import com.inventory.model.Product;

public class InventoryServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("user") == null) {
            resp.sendRedirect("index.jsp");
            return;
        }
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:database/inventory.db");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM products");
            List<Product> products = new ArrayList<>();

            while (rs.next()) {
                Product p = new Product();
                p.id = rs.getInt("id");
                p.name = rs.getString("name");
                p.quantity = rs.getInt("quantity");
                p.price = rs.getDouble("price");
                products.add(p);
            }

            req.setAttribute("products", products);
            req.getRequestDispatcher("inventory.jsp").forward(req, resp);

        } catch (Exception e) {
            resp.getWriter().write(e.getMessage());
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("user") == null) {
            resp.sendRedirect("index.jsp");
            return;
        }
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        double price = Double.parseDouble(req.getParameter("price"));

        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:database/inventory.db");
            Statement stmt = conn.createStatement();
            if (id == null || id.isEmpty()) {
                stmt.executeUpdate("INSERT INTO products (name, quantity, price) VALUES ('" + name + "', "
                        + quantity + ", " + price + ")");
            } else {
                stmt.executeUpdate("UPDATE products SET name='" + name + "', quantity=" + quantity + ", price="
                        + price + " WHERE id=" + id);
            }
            resp.sendRedirect("inventory");
        } catch (IOException | ClassNotFoundException | SQLException e) {
            resp.getWriter().write(e.getMessage());
        }
    }

    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();
        resp.sendRedirect("index.jsp");
    }
}