<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>Add New Order</title>
        <%@ include file="header.jsp" %>
        <style>
            .form-container {
                width: 80%;
                margin: 0 auto;
            }
        </style>
        <script>
            function addItem() {
                var itemDiv = document.createElement('div');
                itemDiv.className = 'form-group';
                itemDiv.innerHTML = `
                    <label for="productId">Product:</label>
                    <select class="form-control" name="productId[]" required onchange="updatePrice(this)"></select>
                    <label for="quantity">Quantity:</label>
                    <input type="number" class="form-control" name="quantity[]" required>
                    <label for="pricePerUnit">Price per Unit:</label>
                    <input type="number" step="0.01" class="form-control" name="pricePerUnit[]" required>
                `;
                document.getElementById('orderItems').appendChild(itemDiv);
                fetchProductIds(itemDiv.querySelector('select[name="productId[]"]'));
            }

            function fetchProductIds(selectElement) {
                fetch('/getProductIds')
                    .then(response => response.json())
                    .then(data => {
                        data.forEach(product => {
                            var option = document.createElement('option');
                            option.value = product.id;
                            option.text = product.name;
                            option.setAttribute('data-price', product.price);
                            selectElement.appendChild(option);
                        });
                        updatePrice(selectElement); // Call updatePrice after options are loaded
                    })
                    .catch(error => console.error('Error fetching product IDs:', error));
            }

            function updatePrice(selectElement) {
                var selectedOption = selectElement.options[selectElement.selectedIndex];
                var price = selectedOption.getAttribute('data-price');
                var priceInput = selectElement.parentElement.querySelector('input[name="pricePerUnit[]"]');
                priceInput.value = price;
            }

            document.addEventListener('DOMContentLoaded', function() {
                var productSelects = document.querySelectorAll('select[name="productId[]"]');
                productSelects.forEach(function(selectElement) {
                    fetchProductIds(selectElement);
                });
            });
        </script>
    </head>
<body>
    <%@ include file="toolbar.jsp" %>
    <h1>Add New Order</h1>
    <div class="form-container">
        <form action="saveOrderServlet" method="post" name="addOrderForm">
            <div class="form-group">
                <label for="userId">User ID:</label>
                <input type="text" class="form-control" id="userId" name="userId" required>
            </div>
            <div id="orderItems">
                <h3>Order Items</h3>
                <div class="form-group">
                    <label for="productId">Product:</label>
                    <select class="form-control" name="productId[]" required onchange="updatePrice(this)"></select>
                    <label for="quantity">Quantity:</label>
                    <input type="number" class="form-control" name="quantity[]" required>
                    <label for="pricePerUnit">Price per Unit:</label>
                    <input type="number" step="0.01" class="form-control" name="pricePerUnit[]" required>
                </div>
            </div>
            <button type="button" class="btn btn-secondary" onclick="addItem()">Add Another Item</button>
            <button type="submit" class="btn btn-primary">Save Order</button>
        </form>
    </div>
    <%@ include file="footer.jsp" %>
</body>
</html>
