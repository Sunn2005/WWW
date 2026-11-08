<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Product Detail</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cart.css">
</head>
<body>
<div class="detail-page">
    <h2>Product Detail</h2>
    <c:if test="${not empty product}">
        <ul>
            <li>Id: ${product.id}</li>
            <li>Model: ${product.model}</li>
            <li>Description: ${product.description}</li>
            <li>Quantity: ${product.quantity}</li>
            <li>Price: ${product.price}</li>
        </ul>
        <img src="${pageContext.request.contextPath}/images/${product.imgUrl}"
             alt="${product.model}"
             width="150">
    </c:if>
    <p>
        <a href="${pageContext.request.contextPath}/products">Back to Product List</a>
    </p>
</div>
</body>
</html>
