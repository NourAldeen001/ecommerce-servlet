<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="entities.Product" %>
<html>
<head><title>المنتجات</title></head>
<body>
    <h2>قائمة المنتجات</h2>
    <a href="${pageContext.request.contextPath}/cart">عرض السلة</a>
    <hr/>

    <% String error = (String) request.getAttribute("error");
       if (error != null) { %>
        <p style="color:red"><%= error %></p>
    <% } %>

    <table border="1" cellpadding="8">
        <tr><th>الاسم</th><th>الوصف</th><th>السعر</th><th>المتاح</th><th>الكمية</th><th></th></tr>
        <%
            List<Product> products = (List<Product>) request.getAttribute("products");
            for (Product p : products) {
        %>
            <tr>
                <td><%= p.getName() %></td>
                <td><%= p.getDescription() %></td>
                <td><%= String.format("%.2f", p.getPrice()) %></td>
                <td><%= p.getStock() %></td>
                <td>
                    <form action="${pageContext.request.contextPath}/cart" method="post" style="display:inline;">
                        <input type="hidden" name="action" value="add"/>
                        <input type="hidden" name="productId" value="<%= p.getId() %>"/>
                        <input type="number" name="quantity" value="1" min="1" max="<%= p.getStock() %>" style="width:60px;"/>
                        <button type="submit" <%= p.getStock() == 0 ? "disabled" : "" %>>
                            <%= p.getStock() == 0 ? "غير متوفر" : "أضف للسلة" %>
                        </button>
                    </form>
                </td>
            </tr>
        <%
            }
        %>
    </table>
</body>
</html>
