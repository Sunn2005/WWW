<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Cart</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cart.css">
</head>
<body>
<div class="cart-page">
    <h1>Cart</h1>

    <c:if test="${empty cart.items}">
        <p>Cart is emppty!</p>
    </c:if>

    <c:if test="${not empty cart.items}">
        <table class="cart-table">
            <tr>
                <th>Model</th>
                <th>Quantity</th>
                <th class="num">Price</th>
                <th class="num">SubTotal</th>
                <th>Action</th>
            </tr>
            <c:forEach var="item" items="${cart.items}">
                <tr>
                    <td>${item.product.model}</td>
                    <td>
                        <form action="${pageContext.request.contextPath}/cart" method="post">
                            <input type="hidden" name="action" value="update">
                            <input type="hidden" name="productId" value="${item.product.id}">
                            <input type="number" name="quantity" value="${item.quantity}" min="1">
                            <input type="submit" value="Cập nhật">
                        </form>
                    </td>
                    <td class="num">${item.product.price}</td>
                    <td class="num subtotal">${item.subtotal} VND</td>
                    <td>
                        <form action="${pageContext.request.contextPath}/cart" method="post">
                            <input type="hidden" name="action" value="remove">
                            <input type="hidden" name="productId" value="${item.product.id}">
                            <input type="submit" value="Xóa">
                        </form>
                    </td>
                </tr>
            </c:forEach>
            <tr class="total-row">
                <td colspan="3"><strong>Total:</strong></td>
                <td class="num"><strong>${cart.total} VND</strong></td>
                <td></td>
            </tr>
        </table>

        <form class="clear-form" action="${pageContext.request.contextPath}/cart" method="post">
            <input type="hidden" name="action" value="clear">
            <input type="submit" value="Xóa hết giỏ hàng">
        </form>
    </c:if>

    <p>
        <a href="${pageContext.request.contextPath}/products">Tiếp tục mua</a>
    </p>
</div>
</body>
</html>
