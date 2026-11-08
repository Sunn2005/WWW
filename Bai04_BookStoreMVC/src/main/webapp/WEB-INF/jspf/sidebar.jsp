<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<aside class="sidebar">
    <div class="side-box">
        <h6>ABOUT US</h6>
        <p>About us information will be here.... <a href="${pageContext.request.contextPath}/books">Read More »</a></p>
    </div>
    <div class="side-box">
        <h6>SEARCH SITE</h6>
        <form action="${pageContext.request.contextPath}/books" method="get">
            <input class="form-control form-control-sm" type="text" name="keyword" value="${keyword}">
        </form>
    </div>
    <c:if test="${showCartLink}">
        <a class="cart-link" href="${pageContext.request.contextPath}/cart">Shopping cart (${pageContext.session.id})</a>
    </c:if>
</aside>
