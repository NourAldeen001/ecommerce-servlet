<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="dto.CartItemDto" %>
<html>
<head><title>السلة</title></head>
<body>
    <h2>سلة المشتريات</h2>
    <a href="${pageContext.request.contextPath}/products">تصفح المنتجات</a>
    <hr/>

    <% String error = (String) request.getAttribute("error");
       if (error != null) { %>
        <p style="color:red"><%= error %></p>
    <% } %>

    <%
        List<CartItemDto> cartItems = (List<CartItemDto>) request.getAttribute("cartItems");
        if (cartItems == null || cartItems.isEmpty()) {
    %>
        <p>السلة فارغة</p>
    <%
        } else {
    %>
        <table border="1" cellpadding="8">
            <tr><th>المنتج</th><th>السعر الحالي</th><th>الكمية</th><th>الإجمالي</th><th></th></tr>
            <%
                for (CartItemDto item : cartItems) {
            %>
                <tr>
                    <td>
                        <%= item.getProductName() %>
                        <% if (item.getQuantity() > item.getAvailableStock()) { %>
                            <br/><small style="color:red">
                                فقط <%= item.getAvailableStock() %> متاح حاليًا
                            </small>
                        <% } %>
                    </td>
                    <td><%= String.format("%.2f", item.getCurrentPrice()) %></td>
                    <td>
                        <form action="${pageContext.request.contextPath}/cart" method="post" style="display:inline;">
                            <input type="hidden" name="action" value="update"/>
                            <input type="hidden" name="cartItemId" value="<%= item.getCartItemId() %>"/>
                            <input type="number" name="quantity" value="<%= item.getQuantity() %>" min="1"
                                   max="<%= item.getAvailableStock() %>" style="width:60px;"/>
                            <button type="submit">تحديث</button>
                        </form>
                    </td>
                    <td><%= String.format("%.2f", item.getSubtotal()) %></td>
                    <td>
                        <form action="${pageContext.request.contextPath}/cart" method="post" style="display:inline;">
                            <input type="hidden" name="action" value="remove"/>
                            <input type="hidden" name="cartItemId" value="<%= item.getCartItemId() %>"/>
                            <button type="submit">حذف</button>
                        </form>
                    </td>
                </tr>
            <%
                }
            %>
        </table>

        <%
            double total = (Double) request.getAttribute("cartTotal");
        %>
        <h3>الإجمالي: <%= String.format("%.2f", total) %></h3>
        <a href="${pageContext.request.contextPath}/checkout">
            <button>إتمام الشراء</button>
        </a>
    <%
        }
    %>
</body>
</html>