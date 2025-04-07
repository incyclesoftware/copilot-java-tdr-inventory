<%@ page import="java.util.List,com.inventory.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <%@ include file="header.jsp" %>
    </head>
<body>
    <%@ include file="toolbar.jsp" %>
    <h1>Customer Management</h1>

    <br>
    <a href="addCustomer.jsp">Add New Customer</a>
    <%@ include file="footer.jsp" %>
    <% List<User> customers = (List<User>) request.getAttribute("customers");
    if (customers != null && !customers.isEmpty()) { %>
    <table class="table table-striped table-bordered" width="80%">
        <thead class="thead-dark"></thead>
            <tr>
                <th>ID</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
        <% for(User c : customers) { %>
            <tr>
                <td><%= c.id %></td>
                <td><%= c.firstName %></td>
                <td><%= c.lastName %></td>
                <td><%= c.email %></td>
                <td><%= c.phone %></td>
                <td>
                    <a href="customers?id=<%= c.id %>" class="btn btn-primary">Edit</a>
                </td>
            </tr>
        <% } %>
        </tbody>
    </table>
    <% } else { %>
    <h2>No Customers Found</h2>
    <% } %>
</body>
</html>
