
<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Products | Ecommerce</title>

   <link rel="stylesheet" href="${pageContext.request.contextPath}/view/Product.css">

</head>

<body>


<!-- Header -->

<header class="header">

    <div class="logo">
        Ecommerce
    </div>

    <span>
        Products
    </span>

</header>


<!-- Main -->

<main class="container">


    <div class="page-title">

        <h1>Our Products</h1>

        <p>
            Browse our products and find what you need.
        </p>

    </div>


    <!-- Filters -->

    <div class="filters">


        <!-- Search -->

        <form class="search-form"
              method="post"
              action="${pageContext.request.contextPath}/ProductDetails">

            <input type="text"
                   name="productName"
                   placeholder="Search product by name">

            <button type="submit"
                    class="search-btn">
                Search
            </button>

        </form>


        <!-- Category Filter -->

        <form class="category-form"
              method="post"
              action="${pageContext.request.contextPath}/ShowProducts">

            <select name="categoryId">

                <option value="">
                    All Categories
                </option>

                <c:forEach var="category"
                           items="${categories}">

                    <option value="${category.id}">
                        ${category.name}
                    </option>

                </c:forEach>

            </select>

            <button type="submit"
                    class="filter-btn">
                Filter
            </button>

        </form>

    </div>


    <!-- Products -->

    <c:choose>

        <c:when test="${not empty products}">

            <div class="products-grid">

                <c:forEach var="product"
                           items="${products}">

                    <div class="product-card">


                        <!-- Product Image -->

                        <img class="product-image"
     src="${pageContext.request.contextPath}/ProductImage?id=${product.id}"
     alt="${product.name}">

                        <div class="product-content">

                            <div class="product-name">
                                ${product.name}
                            </div>


                            <div class="product-description">
                                ${product.description}
                            </div>


                            <div class="product-price">
                                $${product.price}
                            </div>


                            <div class="product-stock">

                                <c:choose>

                                    <c:when test="${product.stock > 0}">
                                        In Stock: ${product.stock}
                                    </c:when>

                                    <c:otherwise>
                                        Out of Stock
                                    </c:otherwise>

                                </c:choose>

                            </div>


                            <!-- Product Details -->

                            <a class="details-btn"
                               href="${pageContext.request.contextPath}/ProductDetails?id=${product.id}">
                                View Details
                            </a>

                        </div>

                    </div>

                </c:forEach>

            </div>

        </c:when>


        <c:otherwise>

            <div class="empty">

                <h2>No Products Found</h2>

                <p>
                    Try another search or category.
                </p>

            </div>

        </c:otherwise>

    </c:choose>


</main>

</body>

</html>

