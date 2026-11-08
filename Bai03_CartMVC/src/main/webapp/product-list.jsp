<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Product List</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cart.css">
</head>
<body>
<p class="view-cart">
    <a href="${pageContext.request.contextPath}/cart">View Cart</a>
</p>

<div class="products">
    <c:forEach items="${products}" var="p">
        <div class="product-class">
            <b>${p.model}</b>
            <img src="${pageContext.request.contextPath}/images/${p.imgUrl}"
                 alt="${p.model}"
                 class="hinh">
            <div class="price-line">Price: ${p.price}</div>
            <form action="${pageContext.request.contextPath}/cart" method="post">
                <input type="text" size="2" value="1" name="quantity"><br>
                <input type="hidden" name="id" value="${p.id}">
                <input type="hidden" name="price" value="${p.price}">
                <input type="hidden" name="model" value="${p.model}">
                <input type="hidden" name="action" value="add">
                <input type="submit" name="addToCart" value="Add To Cart">
            </form>
            <a href="${pageContext.request.contextPath}/product?id=${p.id}">Product Detail</a>
        </div>
    </c:forEach>
</div>
</body>
</html>
