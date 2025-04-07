<%@ page import="java.util.List,com.inventory.model.Order" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <%@ include file="header.jsp" %>
    </head>
<body>
    <%@ include file="toolbar.jsp" %>
    <h1>Order Management</h1>
    <% List<Order> orders = (List<Order>) request.getAttribute("orders");
    if (orders != null && !orders.isEmpty()) { %>
    <table class="table table-striped table-bordered" width="80%">
        <thead class="thead-dark"></thead>
            <tr>
                <th>ID</th>
                <th>User ID</th>
                <th>Order Date</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
        <% for(Order o : orders) { %>
            <tr>
                <td><%= o.id %></td>
                <td><%= o.userId %></td>
                <td><%= o.orderDateTime %></td>
                <td>
                    <a href="orders?id=<%= o.id %>" class="btn btn-primary">Edit</a>
                    <a href="deleteOrder?id=<%= o.id %>" class="btn btn-danger">Delete</a>
                </td>
            </tr>
        <% } %>
        </tbody>
    </table>
    <% } else { %>
    <h2>No Orders Found</h2>
    <% } %>
    <br>
    <a href="addOrder.jsp">Add New Order</a>
    <%@ include file="footer.jsp" %>
</body>
</html>