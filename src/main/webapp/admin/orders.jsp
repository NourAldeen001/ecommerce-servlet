<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<!DOCTYPE html>
<html>

<head>
    <title>Orders</title>
</head>

<body>
<h2>Orders</h2>
<a href="dashboard">Return to dashboard</a>
<br/>
<h3>Pending</h3>
<c:choose>
    <c:when test="${pending.size() > 0}">
        <table>
            <tr>
                <th>ID</th>
                <th>Customer Name</th>
                <th>Customer Address</th>
                <th>Total Amount</th>
                <th>Order Status</th>
                <th>Edit</th>
                <th>Cancel</th>
            </tr>
            <c:forEach var="item" items="${pending}">
                <tr>
                    <td>${item.id}</td>
                    <td>${item.customerName}</td>
                    <td>${item.customerAddress}</td>
                    <td>${item.total}</td>
                    <td>${item.status}</td>
                    <td><a href="orders/update?order-id=${item.id}">Edit</a></td>
                    <td><a href="orders/cancel?order-id=${item.id}">Cancel</a></td>
                </tr>
            </c:forEach>
        </table>

</c:when>
    <c:otherwise>
        <p>There are no pending orders</p>
    </c:otherwise>
</c:choose>
<br/><br/>

<h3>Confirmed</h3>

<c:choose>
    <c:when test="${confirmed.size() > 0}">
        <table>
            <tr>
                <th>ID</th>
                <th>Customer Name</th>
                <th>Customer Address</th>
                <th>Total Amount</th>
                <th>Order Status</th>
                <th>Edit</th>
                <th>Cancel</th>
            </tr>
            <c:forEach var="item" items="${confirmed}">
                <tr>
                    <td>${item.id}</td>
                    <td>${item.customerName}</td>
                    <td>${item.customerAddress}</td>
                    <td>${item.total}</td>
                    <td>${item.status}</td>
                    <td><a href="orders/update?order-id=${item.id}">Edit</a></td>
                    <td><a href="orders/cancel?order-id=${item.id}">Cancel</a></td>
                </tr>
            </c:forEach>
        </table>
    </c:when>
    <c:otherwise>
        <p>There are no confirmed orders</p>
    </c:otherwise>
</c:choose>


<br/><br/>


<h3>Shipped</h3>
<c:choose>
    <c:when test="${shipped.size() > 0}">
        <table>
            <tr>
                <th>ID</th>
                <th>Customer Name</th>
                <th>Customer Address</th>
                <th>Total Amount</th>
                <th>Order Status</th>
                <th>Edit</th>
                <th>Cancel</th>
            </tr>
            <c:forEach var="item" items="${shipped}">
                <tr>
                    <td>${item.id}</td>
                    <td>${item.customerName}</td>
                    <td>${item.customerAddress}</td>
                    <td>${item.total}</td>
                    <td>${item.status}</td>
                    <td><a href="orders/update?order-id=${item.id}">Edit</a></td>
                    <td><a href="orders/cancel?order-id=${item.id}">Cancel</a></td>
                </tr>
            </c:forEach>
        </table>
    </c:when>
    <c:otherwise>
        <p>There are no shipped orders</p>
    </c:otherwise>
</c:choose>

<br/><br/>
<h3>Delivered</h3>
<c:choose>
    <c:when test="${delivered.size() > 0}">
        <table>
            <tr>
                <th>ID</th>
                <th>Customer Name</th>
                <th>Customer Address</th>
                <th>Total Amount</th>
                <th>Order Status</th>
                <th>Edit</th>
                <th>Cancel</th>
            </tr>
            <c:forEach var="item" items="${delivered}">
                <tr>
                    <td>${item.id}</td>
                    <td>${item.customerName}</td>
                    <td>${item.customerAddress}</td>
                    <td>${item.total}</td>
                    <td>${item.status}</td>
                    <td><a href="orders/update?order-id=${item.id}">Edit</a></td>
                    <td><a href="orders/cancel?order-id=${item.id}">Cancel</a></td>
                </tr>
            </c:forEach>
        </table>
    </c:when>
    <c:otherwise>
        <p>There are no delivered orders</p>
    </c:otherwise>
</c:choose>
<br/><br/>

<h3>Cancelled</h3>
<c:choose>
    <c:when test="${cancelled.size() > 0}">
        <table>
            <tr>
                <th>ID</th>
                <th>Customer Name</th>
                <th>Customer Address</th>
                <th>Total Amount</th>
                <th>Order Status</th>
            </tr>
            <c:forEach var="item" items="${cancelled}">
                <tr>
                    <td>${item.id}</td>
                    <td>${item.customerName}</td>
                    <td>${item.customerAddress}</td>
                    <td>${item.total}</td>
                    <td>${item.status}</td>
                </tr>
            </c:forEach>
        </table>
    </c:when>
    <c:otherwise>
        <p>There are no cancelled orders</p>
    </c:otherwise>
</c:choose>

<br/><br/>

</body>