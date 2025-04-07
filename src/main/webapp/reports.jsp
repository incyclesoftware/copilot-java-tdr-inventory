<%@ page import="java.util.List,com.inventory.model.Order" %>
<%@ page import="java.util.List,com.inventory.model.Product" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <%@ include file="toolbar.jsp" %>
    <div class="container mt-5">
        <h1 class="mb-4">Reports</h1>
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Quantity</th>
                    <th>Price</th>
                </tr>
            </thead>
            <tbody>
                <% List<Product> products = (List<Product>) request.getAttribute("products");
                for (Product p : products) { %>
                    <tr>
                        <td><%= p.id %></td>
                        <td><%= p.name %></td>
                        <td><%= p.quantity %></td>
                        <td><%= p.price %></td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    </div>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
