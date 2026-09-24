<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<!DOCTYPE html>
<html>

<head>
    <title>Update Order</title>
</head>

<body>
<h2>Update Order</h2>

<form action="update" method="post">
    <p><strong>Order ID:</strong>${order.id}</p>
    <p><strong>Customer:</strong>${order.customer.name}</p>

    <p><strong>Total Amount:</strong>${order.totalAmount}</p>

    <p><strong>Date:</strong>${order.date}</p>

    <p><strong>Current Status:</strong>${order.status}</p>

    <h3>Order Items</h3>

    <table>
        <tr>
            <th>ID</th>
            <th>Quantity</th>
            <th>Price</th>
        </tr>

        <c:forEach var="item" items="${order.orderItems}">
            <tr>
                <td>${item.id}</td>
                <td>${item.quantity}</td>
                <td>${item.price}</td>
            </tr>
        </c:forEach>
    </table>

    <br />

    <label>Change Status:</label>

    <select name="status">
        <option value="PENDING">PENDING</option>
        <option value="CONFIRMED">CONFIRMED</option>
        <option value="SHIPPED">SHIPPED</option>
        <option value="DELIVERED">DELIVERED</option>
    </select>

    <br /><br />

    <input type="hidden" name="order-id" value="${order.id}" />

    <input type="submit" value="Update Status" />
</form>

<br />

<a href="orders">View Orders</a>
</body>

</html>
