<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 21/9/26
  Time: 22:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head>
    <title>Add department page </title>
</head>
<body>
<a href="employees">Department list page</a>

<form action="employees" method="post">
    <input type="hidden" name="action" value="SAVE">
    <c:if test="${not empty employee}">
        <input type="hidden" name="id" value="${employee.id}">
    </c:if>
    <div>
        <label>Name</label>
        <input type="text" name="name" value="${employee.name}">
    </div>
    <div>
        <label>Salary</label>
        <input type="text" name="salary" value="${employee.salary}">
    </div>
    <div>
        <label>Department name</label>
        <select name="deptId">
            <c:forEach items="${departments}" var="d">
                <option value="${d.id}">${d.name}</option>
            </c:forEach>
        </select>

    </div>

    <button type="submit">Add</button>
</form>
</body>
</html>
