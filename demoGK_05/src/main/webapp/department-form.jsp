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
<a href="departments">Department list page</a>

<form action="departments" method="post">
    <input type="hidden" name="action" value="SAVE">
    <c:if test="${not empty department}">
        <input type="hidden" name="id" value="${department.id}">
    </c:if>
    <label>name</label>
    <input type="text" name="name" value="${department.name}">
    <button type="submit">Add</button>
</form>
</body>
</html>
