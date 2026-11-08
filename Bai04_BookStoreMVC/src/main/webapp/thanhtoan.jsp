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
    <title>Thanh toán</title>
</head>
<body>
<c:set var="showCartLink" value="true" scope="request"/>
<div class="store-wrap">
    <jsp:include page="/WEB-INF/jspf/header.jsp"/>
    <div class="store-body">
        <jsp:include page="/WEB-INF/jspf/sidebar.jsp"/>
        <main class="content">
            <h6 class="text-center">Checkout - Already registered?</h6>
            <c:if test="${not empty message}">
                <div class="alert alert-success">${message}. Khách hàng: ${fullname}. Tổng: <fmt:formatNumber value="${paidTotal}" maxFractionDigits="0"/> VND</div>
            </c:if>
            <form action="${pageContext.request.contextPath}/checkout" method="post">
                <table class="pay-table">
                    <tr>
                        <td class="label">Fullname:</td>
                        <td><input class="form-control form-control-sm" type="text" name="fullname" required value="${fullname}"></td>
                    </tr>
                    <tr>
                        <td class="label">Shipping address:</td>
                        <td><input class="form-control form-control-sm" type="text" name="address" required value="${address}"></td>
                    </tr>
                    <tr>
                        <td class="label">Total price:</td>
                        <td><input class="form-control form-control-sm" type="text" readonly
                                   value="<fmt:formatNumber value='${empty paidTotal ? cart.total : paidTotal}' maxFractionDigits='0'/>"></td>
                    </tr>
                    <tr>
                        <td class="label">Payment method:</td>
                        <td>
                            <label><input type="radio" name="payment" value="Paypal" checked> Paypal</label>
                            <label><input type="radio" name="payment" value="ATM Debit"> ATM Debit</label>
                            <label><input type="radio" name="payment" value="Visa/Master card"> Visa/Master card</label>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>
                            <button class="btn btn-secondary btn-sm" type="submit">Save</button>
                            <a class="btn btn-outline-secondary btn-sm" href="${pageContext.request.contextPath}/cart">Cancel</a>
                        </td>
                    </tr>
                </table>
            </form>
        </main>
    </div>
</div>
</body>
</html>
