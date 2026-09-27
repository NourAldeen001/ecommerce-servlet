<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home | S-Market</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/view/Product.css">
</head>
<body>

<jsp:include page="/view/NavBar.jsp" />

<main class="container">

    <div class="hero">
        <h1>Welcome<c:if test="${not empty customerName}">, ${customerName}</c:if>!</h1>
        <p>Browse our products, build your cart, and check out whenever you're ready.</p>
        <a class="details-btn" style="display:inline-block; width:auto; padding:13px 26px;"
           href="${pageContext.request.contextPath}/ShowProducts">Start Shopping</a>
    </div>

    <div class="quick-links">

        <a class="quick-link" href="${pageContext.request.contextPath}/ShowProducts">
            Browse Products
            <span class="quick-link-desc">See everything we have in stock</span>
        </a>

        <a class="quick-link" href="${pageContext.request.contextPath}/cart">
            View Cart
            <span class="quick-link-desc">Review items before checkout</span>
        </a>

        <a class="quick-link" href="${pageContext.request.contextPath}/orders/my">
            My Orders
            <span class="quick-link-desc">Track orders you've placed</span>
        </a>

    </div>

</main>

</body>
</html>
