<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 16/9/26
  Time: 16:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>--%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Department form </title>
</head>
<body>
<a href="departments" style="display: block">Add department page</a>

<form action="departments" method="post">
    <input type="hidden" name="action" value="SAVE">
    <c:if test="${not empty department}">
        <input type="hidden" name="id" value="${department.id}">
    </c:if>

    <label>Name</label>
    <input name="name" value="${department.name}">
    <button type="submit">Save</button>
</form>

</body>
</html>
