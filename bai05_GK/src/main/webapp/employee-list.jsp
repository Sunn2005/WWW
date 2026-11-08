<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 16/9/26
  Time: 16:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Employee List Page</title>
</head>
<body>
<a href="employees?action=CREATE" style="display: block">Add employee page</a>
<a href="departments" style="display: block">Department list page</a>

<form method="get" action="employees">
    <label>Tìm nhân viên</label>
    <input name="name">
    <button type="submit">Search</button>
</form>


<table border="1" width="80%">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Salary</th>
        <th>Department</th>
        <th>Action</th>
    </tr>

    <c:forEach items="${employees}" var="e">
        <tr>
            <td>${e.id}</td>
            <td>${e.name}</td>
            <td>${e.salary}</td>
            <td>${e.department.name}</td>
            <td style="display: flex; align-items: center;">
                <a href="employees?action=EDIT&id=${e.id}">Edit</a>
                <form action="employees" method="post">
                    <input type="hidden" name="action" value="DELETE">
                    <input type="hidden" name="id" value="${e.id}">
                    <button type="submit">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>

</table>
</body>
</html>
