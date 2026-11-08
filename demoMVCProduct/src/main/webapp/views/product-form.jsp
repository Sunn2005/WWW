<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>${formAction == 'insert' ? 'Thêm sản phẩm' : 'Cập nhật sản phẩm'}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<main class="container form-container">
    <h1>${formAction == 'insert' ? 'Thêm sản phẩm' : 'Cập nhật sản phẩm'}</h1>

    <c:if test="${not empty error}">
        <p class="error">${error}</p>
    </c:if>

    <form class="product-form" action="${pageContext.request.contextPath}/products" method="post">
        <input type="hidden" name="action" value="${formAction}">

        <label for="id">Mã sản phẩm</label>
        <input id="id" name="id" type="text" value="${fn:escapeXml(product.id)}"
               required maxlength="20" ${formAction == 'update' ? 'readonly' : ''}
               placeholder="Ví dụ: P011">

        <label for="name">Tên sản phẩm</label>
        <input id="name" name="name" type="text" value="${fn:escapeXml(product.name)}" required maxlength="255">

        <label for="price">Giá</label>
        <input id="price" name="price" type="number" value="${product.price}"
               required min="0" step="0.01">

        <div class="form-actions">
            <button class="button" type="submit">Lưu</button>
            <a class="button secondary" href="${pageContext.request.contextPath}/products">Hủy</a>
        </div>
    </form>
</main>
</body>
</html>
