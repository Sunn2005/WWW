<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chi tiết sản phẩm</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<main class="container form-container">
    <h1>Chi tiết sản phẩm</h1>
    <dl class="details">
        <dt>Mã sản phẩm</dt>
        <dd>${product.id}</dd>
        <dt>Tên sản phẩm</dt>
        <dd><c:out value="${product.name}" /></dd>
        <dt>Giá</dt>
        <dd>${product.price} VNĐ</dd>
    </dl>
    <div class="form-actions">
        <a class="button" href="${pageContext.request.contextPath}/products?action=edit&id=${product.id}">Sửa</a>
        <a class="button secondary" href="${pageContext.request.contextPath}/products">Quay lại</a>
    </div>
</main>
</body>
</html>
