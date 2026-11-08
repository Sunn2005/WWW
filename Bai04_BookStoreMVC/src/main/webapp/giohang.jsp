<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 24/9/26
  Time: 11:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head>
    <title>Giỏ hàng</title>
</head>
<body>
<%--<c:set var="showCartLink" value="true" scope="request"/>--%>
<div class="store-wrap">
    <jsp:include page="/WEB-INF/jspf/header.jsp"/>
    <div class="store-body">
        <jsp:include page="/WEB-INF/jspf/sidebar.jsp"/>
        <main class="content">
            <h6 class="text-center">YOUR SHOPPING CART</h6>
            <table class="cart-table">
                <tr>
                    <th>Product ID</th>
                    <th>Product name</th>
                    <th>Price</th>
                    <th>Qty</th>
                    <th>Total</th>
                    <th>Remove</th>
                </tr>
                <c:forEach var="item" items="${cart.items}">
                    <tr>
                        <td>pro${item.book.id}</td>
                        <td>${item.book.tittle} - Tác giả: ${item.book.author}</td>
                        <td><fmt:formatNumber value="${item.book.price}" maxFractionDigits="0"/></td>
                        <td class="text-center">${item.qty}</td>
                        <td><fmt:formatNumber value="${item.lineTotal}" maxFractionDigits="0"/></td>
                        <td class="text-center">
                            <form action="${pageContext.request.contextPath}/cart" method="post">
                                <input type="hidden" name="action" value="remove">
                                <input type="hidden" name="id" value="${item.book.id}">
                                <button class="btn btn-link btn-sm p-0" type="submit">Remove</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <td colspan="4"></td>
                    <td colspan="2">Total price (VND): <fmt:formatNumber value="${cart.total}" maxFractionDigits="0"/></td>
                </tr>
            </table>
            <div class="cart-actions">
                <a class="btn btn-outline-secondary btn-sm" href="${pageContext.request.contextPath}/checkout">Checkout</a>
                <a class="btn btn-outline-secondary btn-sm" href="${pageContext.request.contextPath}/books">Continue shopping</a>
            </div>
        </main>
    </div>
</div>
</body>
</html>
