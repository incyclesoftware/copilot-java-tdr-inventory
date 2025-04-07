<%@ page import="java.util.List,com.inventory.model.Product" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
    <%@ include file="header.jsp" %>
    </head>
<body>
    <div class="container">
        <h1 class="mt-5">Please Login</h1>
        <% if (request.getParameter("error") != null) { %>
            <div class="alert alert-danger" role="alert">
                Login failed. Please try again.
            </div>
        <% } %>
        <form action="login" method="post" class="mt-3">
            <div class="form-group">
                <label for="username">Username:</label>
                <input type="text" class="form-control" id="username" name="username"/>
            </div>
            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" class="form-control" id="password" name="password"/>
            </div>
            <button type="submit" class="btn btn-primary mt-3">Login</button>
        </form>
    </div>
    <%@ include file="footer.jsp" %>
</body>
</html>