<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>خطأ</title></head>
<body>
    <h2 style="color:red">حدث خطأ</h2>
    <p>${error}</p>
    <a href="${pageContext.request.contextPath}/products">العودة للصفحة الرئيسية</a>
</body>
</html>