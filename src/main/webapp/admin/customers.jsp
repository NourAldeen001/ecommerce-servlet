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
<h2>Customers</h2>
<a href="dashboard">Return to dashboard</a>
<br/>
<table>
<tr>
    <th>ID</th>
    <th>Name</th>
    <th>Email</th>
    <th>Address</th>
    <th>Phone</th>
</tr>
<c:forEach var="item" items="${customers}">
    <tr>
        <td>${item.id}</td>
        <td>${item.name}</td>
        <td>${item.email}</td>
        <td>${item.address}</td>
        <td>${item.phone}</td>
    </tr>
</c:forEach>
</table>
<br/><br/>


</body>