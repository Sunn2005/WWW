<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 16/9/26
  Time: 16:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<html>
<head>
    <title>Employee Form</title>
</head>
<body>
<a href="employees" style="display: block">Add employee page</a>

<form action="employees" method="post">
    <input type="hidden" name="action" value="SAVE">
    <c:if test="${not empty employee}">
        <input type="hidden" name="id" value="${employee.id}">
    </c:if>

    <div>
        <label>Name: </label>
        <input name="name" value="${employee.name}">
    </div>
    <div>
        <label>Salary: </label>
        <input name="salary" value="${employee.salary}">
    </div>
    <div>
        <label>Department:</label>
        <select name="departmentId">
            <c:forEach items="${departments}" var="d">
                <option value="${d.id}">${d.name}</option>
            </c:forEach>
        </select>
    </div>
    <button type="submit">Save</button>
</form>
</body>
</html>
