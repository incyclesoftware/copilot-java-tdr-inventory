<div class="toolbar navbar navbar-expand-lg navbar-light bg-light">
    <div class="navbar-nav">
        <a class="nav-item nav-link" href="inventory">Inventory</a>
        <a class="nav-item nav-link" href="addProduct.jsp">Add Product</a>
        <a class="nav-item nav-link" href="reports">Reports</a>
        <a class="nav-item nav-link" href="customers">Customers</a>
        <a class="nav-item nav-link" href="orders">Orders</a> <!-- Added Orders link -->
        <a class="nav-item nav-link" href="#" onclick="logout()">Sign Out</a>
    </div>
</div>
<hr>
<script>
function logout() {
    fetch('inventory', {
        method: 'DELETE',
        credentials: 'include'
    })
    .then(response => {
        if (response.redirected) {
            window.location.href = response.url;
        }
    })
    .catch(error => console.error('Error:', error));
}
</script>
