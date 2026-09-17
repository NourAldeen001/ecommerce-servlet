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
<h2>Admins</h2>
<a href="admins/create">Create an admin account</a>
<a href="dashboard">Return to dashboard</a>
<br/>
<table>
<tr>
    <th>ID</th>
    <th>Username</th>
</tr>
<c:forEach var="item" items="${admins}">
    <tr>
        <td>${item.id}</td>
        <td>${item.username}</td>
        <td>
        <form action="admins/delete" method="POST">
            <input type="hidden" name="toDelete" value="${item.username}">
            <button type="submit" class="btn-link-style">Delete</button>
        </form>
        </td>
    </tr>
</c:forEach>
</table>
<br/><br/>


</body>