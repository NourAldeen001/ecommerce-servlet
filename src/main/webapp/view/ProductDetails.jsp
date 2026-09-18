<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
 <%@ taglib prefix="c" uri="jakarta.tags.core" %> 
 <!DOCTYPE html>
  <html lang="en">
   <head>
    <meta charset="UTF-8"> 
    <meta name="viewport" content="width=device-width, initial-scale=1.0"> 
    <title>${product.name} | Ecommerce</title> 
   <link rel="stylesheet" href="${pageContext.request.contextPath}/view/Product.css">
              </head> 
              <body> 
              <!-- Header --> 
              <header class="header"> 
              <div class="logo"> Ecommerce </div> 
              </header> <main class="container"> 
              <!-- Back to Products --> 
              <a class="back" href="${pageContext.request.contextPath}/ShowProducts"> ← Back to Products </a> 
              <!-- Product --> 
              <div class="product-details"> 
              <!-- Image --> 
              <div class="image-container"> 
              <c:choose> 
              <c:when test="${not empty product.image}"> 
              <img class="product-image" src="${pageContext.request.contextPath}/ProductImage?id=${product.id}" alt="${product.name}">
               </c:when> 
               <c:otherwise> 
               <p>No Image Available</p> 
               </c:otherwise> 
               </c:choose> 
               </div> <!-- Information --> 
               <div class="product-info"> 
               <h1 class="product-name"> ${product.name} </h1> 
               <p class="product-description"> ${product.description} </p> 
               <div class="price"> $${product.price} </div> 
               <div class="info-row"> 
               <span class="label"> Product ID: </span> 
               <span> ${product.id} </span> 
               </div>
                <div class="info-row">
                 <span class="label"> Stock: </span> 
                 <span class="stock"> 
                 <c:choose> 
                 <c:when test="${product.stock > 0}"> ${product.stock} available </c:when> 
                 <c:otherwise> Out of Stock </c:otherwise> 
                 </c:choose> 
                 </span> 
                 </div> 
                 <div class="buttons"> 
                 <a class="btn back-btn" href="${pageContext.request.contextPath}/ShowProducts"> ← Continue Shopping </a> 
                 <c:if test="${product.stock > 0}"> <a class="btn cart-btn" href="#"> Add to Cart </a> </c:if> 
                 </div> 
                 </div> 
                 </div> 
                 </main> 
                 </body> 
                 </html>