<%@ page import="com.inventory.model.User" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="header.jsp" %>
</head>
<body>
    <%@ include file="toolbar.jsp" %>
    <div class="container mt-5">
        <h1 class="mb-4">Edit Customer</h1>
        <form action="customers" method="post">
            <%
                User customer = (User) request.getAttribute("customer");
                if (customer != null) {
            %>
            <input type="hidden" name="id" value="<%= customer.id %>">
            <div class="form-group">
                Customer Id: <span ><%= customer.id %></span>
            </div>
            <div class="form-group">
                <label for="first_name">First Name:</label>
                <input type="text" class="form-control" id="first_name" name="first_name" value="<%= customer.firstName %>">
            </div>
            <div class="form-group">
                <label for="last_name">Last Name:</label>
                <input type="text" class="form-control" id="last_name" name="last_name" value="<%= customer.lastName %>">
            </div>
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" class="form-control" id="email" name="email" value="<%= customer.email %>">
            </div>
            <div class="form-group">
                <label for="phone">Phone:</label>
                <input type="text" class="form-control" id="phone" name="phone" value="<%= customer.phone %>">
            </div>
            <button type="submit" class="btn btn-primary">Update Customer</button>
            <%
                } else {
            %>
            <p>Customer not found.</p>
            <%
                }
            %>
        </form>
    </div>
    <%@ include file="footer.jsp" %>
</body>
</html>
