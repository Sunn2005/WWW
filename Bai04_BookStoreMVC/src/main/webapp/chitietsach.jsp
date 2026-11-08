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
    <title>Chi tiết sách</title>
</head>
<body>
<div class="store-wrap">
    <jsp:include page="/WEB-INF/jspf/header.jsp"/>
    <div class="store-body">
        <jsp:include page="/WEB-INF/jspf/sidebar.jsp"/>
        <main class="content">
            <c:if test="${book != null}">
                <div class="detail">
                    <img src="${pageContext.request.contextPath}/imgesbook/${book.imgBook}" alt="${book.tittle}">
                    <div>
                        <h5>Product details: ${book.tittle} - Tác giả: ${book.author}</h5>
                        <p>Price (VND): <fmt:formatNumber value="${book.price}" maxFractionDigits="0"/></p>
                        <p>Quantity: ${book.quantity}</p>
                        <form action="${pageContext.request.contextPath}/books" method="post">
                            <input type="hidden" name="id" value="${book.id}">
                            <button class="btn btn-link p-0" type="submit">Add to cart</button>
                        </form>
                        <p><a href="${pageContext.request.contextPath}/books">Back to Product List</a></p>
                    </div>
                </div>
            </c:if>
            <c:if test="${book == null}">
                <p>Không tìm thấy sách.</p>
                <a href="${pageContext.request.contextPath}/books">Back to Product List</a>
            </c:if>
        </main>
    </div>
</div>
</body>
</html>
