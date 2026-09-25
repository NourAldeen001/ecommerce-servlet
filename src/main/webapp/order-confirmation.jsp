<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dto.OrderResponseDto" %>
<html>
<head><title>تأكيد الطلب</title></head>
<body>
    <%
        OrderResponseDto order = (OrderResponseDto) request.getAttribute("order");
        boolean confirmed = "CONFIRMED".equals(order.getStatus());
    %>
    <% if (confirmed) { %>
        <h2 style="color:green">تم تأكيد طلبك بنجاح ✅</h2>
    <% } else { %>
        <h2 style="color:red">حدثت مشكلة في الطلب</h2>
    <% } %>

    <p><strong>رقم الطلب:</strong> <%= order.getOrderId() %></p>
    <p><strong>الإجمالي:</strong> <%= String.format("%.2f", order.getTotalAmount()) %></p>
    <p><strong>الحالة:</strong> <%= order.getStatus() %></p>
    <p><%= order.getMessage() %></p>

    <a href="${pageContext.request.contextPath}/products">العودة للتسوق</a>
</body>
</html>