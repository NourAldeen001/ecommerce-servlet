<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="dto.CartItemDto" %>
<html>
<head><title>إتمام الشراء</title></head>
<body>
    <h2>مراجعة الطلب</h2>

    <% String error = (String) request.getAttribute("error");
       if (error != null) { %>
        <p style="color:red"><%= error %></p>
    <% } %>

    <table border="1" cellpadding="8">
        <tr><th>المنتج</th><th>السعر</th><th>الكمية</th><th>الإجمالي</th></tr>
        <%
            List<CartItemDto> cartItems = (List<CartItemDto>) request.getAttribute("cartItems");
            for (CartItemDto item : cartItems) {
        %>
            <tr>
                <td><%= item.getProductName() %></td>
                <td><%= String.format("%.2f", item.getCurrentPrice()) %></td>
                <td><%= item.getQuantity() %></td>
                <td><%= String.format("%.2f", item.getSubtotal()) %></td>
            </tr>
        <%
            }
            double total = (Double) request.getAttribute("cartTotal");
        %>
    </table>

    <h3>الإجمالي الكلي: <%= String.format("%.2f", total) %></h3>

    <form action="${pageContext.request.contextPath}/checkout" method="post">
        <label>طريقة الدفع:</label>
        <select name="paymentMethod" required>
            <option value="CREDIT_CARD">فيزا / ماستركارد</option>
            <option value="CASH">كاش عند الاستلام</option>
        </select><br/><br/>
        <button type="submit">تأكيد الطلب</button>
    </form>
</body>
</html>