<%@ page import="java.util.List,com.inventory.model.Product" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <%@ include file="header.jsp" %>
    </head>
<body>
    <%@ include file="toolbar.jsp" %>
    <h1>Inventory Management</h1>
    <% List<Product> products = (List<Product>) request.getAttribute("products");
    if (products != null && !products.isEmpty()) { %>
    <table class="table table-striped table-bordered" width="80%">
        <thead class="thead-dark"></thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Quantity</th>
                <th>Price</th>
            </tr>
        </thead>
        <tbody>
        <% for(Product p : products) { %>
            <tr>
            <td><%= p.id %></td>
            <td><%= p.name %></td>
            <td><%= p.quantity %></td>
            <td><%= p.price %></td>
            </tr>
        <% } %>
        </tbody>
    </table>
    <% } else { %>
    <h2>No Products Found</h2>
    <% } %>
    <br>
    <a href="addProduct.jsp">Add New Product</a>
    <%@ include file="footer.jsp" %>
</body>
</html>