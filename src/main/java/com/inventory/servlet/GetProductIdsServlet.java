package com.inventory.servlet;

import java.io.IOException;
import java.io.PrintWriter;
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
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.inventory.model.Product;

@WebServlet("/getProductIds")
public class GetProductIdsServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:sqlite:database/inventory.db";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print("{\"error\":\"User not logged in\"}");
            out.flush();
            return;
        }

        List<Product> products = new ArrayList<>();
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(DB_URL);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT id, name, quantity, price FROM products");
                while (rs.next()) {
                    Product product = new Product();
                    product.id = rs.getInt("id");
                    product.name = rs.getString("name");
                    product.quantity = rs.getInt("quantity");
                    product.price = rs.getDouble("price");
                    products.add(product);
                }
            
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print("{\"error\":\"Unable to fetch product information\"}");
            out.flush();
            return;
        }

        String json = new Gson().toJson(products);
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }
}
