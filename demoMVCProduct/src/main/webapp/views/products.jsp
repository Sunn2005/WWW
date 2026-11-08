<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản lý sản phẩm</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<main class="container">
    <div class="page-heading">
        <div>
            <h1>Quản lý sản phẩm</h1>
            <p>Danh sách sản phẩm hiện có trong cơ sở dữ liệu.</p>
        </div>
        <a class="button" href="${pageContext.request.contextPath}/products?action=new">Thêm sản phẩm</a>
    </div>

    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Tên sản phẩm</th>
            <th>Giá</th>
            <th>Thao tác</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${products}" var="product">
            <tr>
                <td>${product.id}</td>
                <td><c:out value="${product.name}" /></td>
                <td>${product.price} VNĐ</td>
                <td class="actions">
                    <a href="${pageContext.request.contextPath}/products?action=details&id=${product.id}">Chi tiết</a>
                    <a href="${pageContext.request.contextPath}/products?action=edit&id=${product.id}">Sửa</a>
                    <form action="${pageContext.request.contextPath}/products" method="post"
                          onsubmit="return confirm('Bạn có chắc muốn xóa sản phẩm này?');">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="id" value="${product.id}">
                        <button class="link-button danger" type="submit">Xóa</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty products}">
            <tr>
                <td class="empty" colspan="4">Chưa có sản phẩm nào.</td>
            </tr>
        </c:if>
        </tbody>
    </table>
</main>
</body>
</html>
