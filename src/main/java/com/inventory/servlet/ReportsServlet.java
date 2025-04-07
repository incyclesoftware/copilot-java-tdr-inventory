package com.inventory.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.inventory.model.Product;

@WebServlet("/reports")
public class ReportsServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:sqlite:database/inventory.db";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        List<Product> products = new ArrayList<>();
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(DB_URL);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM products");
                while (rs.next()) {
                    Product product = new Product();
                    product.id = rs.getInt("id");
                    product.name = rs.getString("name");
                    product.quantity = rs.getInt("quantity");
                    product.price = rs.getDouble("price");
                    products.add(product);
                }
            }
        catch (Exception e) {
        }

        request.setAttribute("products", products);
        request.getRequestDispatcher("reports.jsp").forward(request, response);
    }
}
