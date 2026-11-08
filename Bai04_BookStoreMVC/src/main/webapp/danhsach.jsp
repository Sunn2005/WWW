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
    <title>IUH Bookstore</title>
</head>
<body>
<div class="store-wrap">
    <jsp:include page="/WEB-INF/jspf/header.jsp"/>
    <div class="store-body">
        <jsp:include page="/WEB-INF/jspf/sidebar.jsp"/>
        <main class="content">
            <div class="book-grid">
                <c:forEach var="book" items="${books}">
                    <article class="book-card">
                        <h6>${book.tittle} - Tác giả: ${book.author}</h6>
                        <img src="${pageContext.request.contextPath}/imgesbook/${book.imgBook}" alt="${book.tittle}">
                        <div>Price: <fmt:formatNumber value="${book.price}" maxFractionDigits="0"/></div>
                        <div>Quantity: ${book.quantity}</div>
                        <div><a href="${pageContext.request.contextPath}/books?action=detail&id=${book.id}">Product details</a></div>
                        <form action="${pageContext.request.contextPath}/books" method="post">
                            <input type="hidden" name="id" value="${book.id}">
                            <button class="btn btn-link btn-sm p-0" type="submit">Add to cart</button>
                        </form>
                    </article>
                </c:forEach>
            </div>
            <c:if test="${empty books}">
                <p>Không tìm thấy sách.</p>
            </c:if>
        </main>
    </div>
</div>
</body>
</html>
